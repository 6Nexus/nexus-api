package com.nexus.backend.controllers;

import com.nexus.backend.dto.PlaylistApiExternaDto;
import com.nexus.backend.dto.VideoApiExternaDto;
import com.nexus.backend.entities.Video;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoControler {
    // EXEMPLO DE PLAYLIST PARAM -> PLj6Dc5_-lwbnYUOlbQ4loX1ekdIkM_7To

    private RestClient client;
    private String token = "AIzaSyCUEY83acY1NloFiY_SP5Bd6LC1B6yVQWs";

    public VideoControler() {
        client = RestClient.builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build();
    }

    @Operation(summary = "Este endpoint busca os vídeos da playlist")
    @GetMapping("playlist/{id}")
    public ResponseEntity<Video[]> buscarVideosPlaylist(@PathVariable String id){
        String url = ("playlistItems?key=%s&part=snippet&playlistId=%s").formatted(token, id);

        PlaylistApiExternaDto raw = client.get()
                                    .uri(url)
                                    .retrieve()
                                    .body(PlaylistApiExternaDto.class);

        Video[] videos = new Video[raw.getItems().size()];

        for (int i = 0; i < videos.length; i++) {
            Video video = new Video();
            video.setId(raw.getVideoIds().get(i));
            video.setTitulo(raw.getTitle().get(i));
            video.setViews(buscarViewsVideo(raw.getVideoIds().get(i)));
            videos[i] = video;
        }

        videos = ordenacaoDescrescente(videos);

        return raw == null ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(videos);
    }

    @Operation(summary = "Este endpoint busca as visualizações de um vídeo")
    public Integer buscarViewsVideo(String videoId){
        String url = ("videos?id=%s&key=%s&part=statistics").formatted(videoId, token);

        VideoApiExternaDto raw = client.get()
                .uri(url)
                .retrieve()
                .body(VideoApiExternaDto.class);

        return raw.getViews();
    }

    public Video[] ordenacaoDescrescente(Video[] videos){
        for (int i = 0; i < videos.length - 1; i++) {
            for (int j = 1; j < videos.length - i; j++) {
                if (videos[j].getViews() > videos[j - 1].getViews()){
                    Video variavelAuxiliarVideo = videos[j];
                    videos[j] = videos[j - 1];
                    videos[j - 1] = variavelAuxiliarVideo;
                }
            }
        }
        return videos;
    }
}
