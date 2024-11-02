package com.nexus.backend.service.curso.video;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.nexus.backend.entities.curso.video.Video;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.video.VideoRepository;
import com.nexus.backend.service.curso.ModuloService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final ModuloService moduloService;
    private final CredencialService credencialService;

    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public List<Video> listarPorModulo(Integer idModulo) {
        return videoRepository.findByModuloIdOrderByOrdemAsc(idModulo);
    }

    public Integer cadastrar(Video video, Integer idModulo) {
        video.setModulo(moduloService.buscarPorId(idModulo));
        Video videoCadastrado = videoRepository.save(video);
        return carregamentoYoutubeManual(videoCadastrado);
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void cronCarregamentoYoutube(){
        List<Video> videosDoDia = videoRepository.findTop5ByCriadoEmFalseOrderByCriadoEmAsc();
        if (!videosDoDia.isEmpty()){
            videosDoDia.stream()
                    .forEach(video -> {
                        try {
                            video.setYoutubeUrl(carregarVideo(video));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        video.setCarregadoNoYoutube(true);
                    });
        }
    }

    public Integer carregamentoYoutubeManual(Video video){
        try {
            video.setYoutubeUrl(carregarVideo(video));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        video.setCarregadoNoYoutube(true);
        video.setPath(null);
        return videoRepository.save(video).getId();
    }

    public String carregarVideo(Video video) throws Exception {
        Credential credencial = credencialService.autorizarYoutube();

        YouTube youtubeService = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credencial)
                .setApplicationName("youtube-upload")
                .build();

        com.google.api.services.youtube.model.Video videoACarregar = new com.google.api.services.youtube.model.Video();
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus("private");
        videoACarregar.setStatus(status);

        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(video.getTitulo());
        snippet.setDescription(video.getDescricao());
        videoACarregar.setSnippet(snippet);


        File videoFile = new File(video.getPath());
        InputStreamContent mediaContent = new InputStreamContent("video/*", new BufferedInputStream(new FileInputStream(videoFile)));
        mediaContent.setLength(videoFile.length());

        YouTube.Videos.Insert videoInsert = youtubeService.videos()
                .insert("snippet,statistics,status", videoACarregar, mediaContent);

        com.google.api.services.youtube.model.Video videoCarregado = videoInsert.execute();
        return videoCarregado.getId();
    }

    public void deletar(Integer idVideo) {
        if (!videoRepository.existsById(idVideo)) throw new EntityNotFoundException("Vídeo");

        //Apagar o vídeo do armazenamento
        videoRepository.deleteById(idVideo);
    }
}
