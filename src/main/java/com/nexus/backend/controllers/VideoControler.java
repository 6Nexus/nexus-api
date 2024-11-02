package com.nexus.backend.controllers;

import com.nexus.backend.ListaObj;
import com.nexus.backend.dto.PlaylistApiExternaDto;
import com.nexus.backend.dto.VideoApiExternaDto;
import com.nexus.backend.entities.Video;
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

    @GetMapping("playlist/{id}")
    public ResponseEntity<ListaObj<Video>> buscarVideosPlaylist(@PathVariable String id){
        String url = ("playlistItems?key=%s&part=snippet&playlistId=%s").formatted(token, id);

        PlaylistApiExternaDto raw = client.get()
                                    .uri(url)
                                    .retrieve()
                                    .body(PlaylistApiExternaDto.class);

        ListaObj<Video> videos = new ListaObj(raw.getItems().size());
//        Video[] videos = new Video[raw.getItems().size()];

        for (int i = 0; i < videos.getTamanho(); i++) {
            Video video = new Video();
            video.setId(raw.getVideoIds().get(i));
            video.setTitulo(raw.getTitle().get(i));
            video.setViews(buscarViewsVideo(raw.getVideoIds().get(i)));
            videos.adiciona(video);
        }

        videos = ordenacaoDescrescente(videos);

        return raw == null ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(videos);
    }

    public Integer buscarViewsVideo(String videoId){
        String url = ("videos?id=%s&key=%s&part=statistics").formatted(videoId, token);

        VideoApiExternaDto raw = client.get()
                .uri(url)
                .retrieve()
                .body(VideoApiExternaDto.class);

        return raw.getViews();
    }

    public ListaObj<Video> ordenacaoDescrescente(ListaObj<Video> videos){
        for (int i = 0; i < videos.getTamanho() - 1; i++) {
            for (int j = 1; j < videos.getTamanho() - i; j++) {
                if (videos.getElemento(j).getViews() > videos.getElemento(j-1).getViews()){
                    Video variavelAuxiliarVideo = videos.getElemento(j);
                    videos.setElemento(j, videos.getElemento(j - 1));
                    videos.setElemento(j - 1, variavelAuxiliarVideo);
                }
            }
        }
        return videos;
    }

    public void pesquisaBinaria(ListaObj<Video> videos, String videoBusca){
        int ind = 0;
        int indTamVetor = videos.getTamanho() - 1;

        while (ind <= indTamVetor) {
            int meio = (ind + indTamVetor) / 2;

            int comparacao = videoBusca.compareToIgnoreCase(videos.getElemento(meio).getTitulo());

            if (comparacao == 0) {
                System.out.println("Elemento encontrado no índice " + meio);
                return;
            } else if (comparacao < 0) {
                indTamVetor = meio - 1;
            } else {
                ind = meio + 1;
            }
        }
        System.out.println("Elemento não encontrado.");
    }
}
