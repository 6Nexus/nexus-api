package com.nexus.backend.service.curso.video;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.nexus.backend.entities.curso.Matricula;
import com.nexus.backend.entities.curso.video.Video;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.video.VideoRepository;
import com.nexus.backend.service.curso.ModuloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final ModuloService moduloService;
    private final CredencialService credencialService;

    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private Path diretorioBase = Path.of(System.getProperty("user.dir") + "/arquivos");

    public List<Video> listarPorModulo(Integer idModulo) {
        return videoRepository.findByModuloIdOrderByOrdemAsc(idModulo);
    }

    public Video buscarPorId(Integer videoId) {
        return videoRepository.findById(videoId).orElseThrow(
                () -> new EntityNotFoundException("Vídeo")
        );
    }

    private String formatarNomeArquivo(String nomeOriginal) {
        return String.format("%s_%s", UUID.randomUUID(), nomeOriginal);
    }

    public Integer cadastrar(Video video, Integer idModulo, MultipartFile file) {
        if (file.isEmpty()) throw new ResponseStatusException(400, "Nenhum arquivo enviado", null);
        if (!this.diretorioBase.toFile().exists()) this.diretorioBase.toFile().mkdir();

        String nomeArquivoFormatado = formatarNomeArquivo(file.getOriginalFilename());
        String filePath = this.diretorioBase + "/" + nomeArquivoFormatado;
        File destino = new File(filePath);
        try {
            file.transferTo(destino);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(422, "Não foi possível salvar o arquivo", null);
        }

        video.setPath(filePath);
        video.setCriadoEm(LocalDateTime.now());
        video.setModulo(moduloService.buscarPorId(idModulo));
        return videoRepository.save(video).getId();
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void cronCarregamentoYoutube(){
        List<Video> videosDoDia = videoRepository.findTop5BycarregadoNoYoutubeFalseOrderByCriadoEmAsc();
        if (!videosDoDia.isEmpty()){
            videosDoDia.stream()
                    .forEach(video -> {
                        String urlDoVideo = "";
                        try {
                            urlDoVideo = carregarVideo(video);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        if (!urlDoVideo.isEmpty()) {
                            video.setYoutubeUrl(urlDoVideo);
                            video.setCarregadoNoYoutube(true);
                            deletarDoServidor(videoRepository.save(video));
                        }
                    });
        }
    }

    public String carregarVideo(Video video) throws Exception {
        Credential credencial = credencialService.autorizarYoutube();

        YouTube youtubeService = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credencial)
                .setApplicationName("youtube-upload")
                .build();

        com.google.api.services.youtube.model.Video videoACarregar = new com.google.api.services.youtube.model.Video();
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus("unlisted");
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

    public void deletarDoServidor(Video video) {
        Path caminhoDoArquivo = Path.of(video.getPath());
        try {
            Files.delete(caminhoDoArquivo);
        } catch (NoSuchFileException e) {
            System.out.println("O arquivo não existe!");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("O diretório não está vazio!");
        } catch (IOException e) {
            System.out.println("Erro ao tentar apagar o arquivo: " + e.getMessage());
        }
    }
}
