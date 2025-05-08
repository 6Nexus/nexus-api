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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final ModuloService moduloService;
    private final CredencialService credencialService;
    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

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
        if (file.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT);

        String nomeArquivoFormatado = formatarNomeArquivo(file.getOriginalFilename());

        try {
            var request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key("video-aula/" + nomeArquivoFormatado)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new ResponseStatusException(422, "Não foi possível salvar o arquivo", null);
        }

        video.setFileName(nomeArquivoFormatado);
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
                            videoRepository.save(video);
                            deleteS3File(video.getFileName());
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

        String key = Paths.get(video.getFileName()).getFileName().toString();

        File tempFile = File.createTempFile("video_temp_", key);
        tempFile.deleteOnExit();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key("video-aula/" + key)
                .build();

        try (ResponseInputStream<GetObjectResponse> s3InputStream = s3Client.getObject(getObjectRequest);
             FileOutputStream fos = new FileOutputStream(tempFile)) {
            s3InputStream.transferTo(fos);
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(tempFile))) {
            InputStreamContent mediaContent = new InputStreamContent("video/*", bis);
            mediaContent.setLength(tempFile.length());

            YouTube.Videos.Insert videoInsert = youtubeService.videos()
                    .insert("snippet,statistics,status", videoACarregar, mediaContent);

            com.google.api.services.youtube.model.Video videoCarregado = videoInsert.execute();

            return videoCarregado.getId();
        }
    }

    private void deleteS3File(String fileName) {
        try {
            var deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key("video-aula/" + fileName)
                    .build();

            s3Client.deleteObject(deleteRequest);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar arquivo do S3");
        }
    }

    @Transactional
    public Video update(int id, Video videoEntidade) {
        if (!videoRepository.existsById(id)) throw new EntityNotFoundException("Video");
        videoEntidade.setId(id);
        return videoRepository.save(videoEntidade);
    }
}
