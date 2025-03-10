package com.nexus.backend.controllers;

import com.nexus.backend.util.IO;
import com.nexus.backend.util.ListaObj;
import com.nexus.backend.dto.PlaylistApiExternaDto;
import com.nexus.backend.dto.VideoApiExternaDto;
import com.nexus.backend.entities.Video;
import com.nexus.backend.util.strategy.IOrdenacao;
import com.nexus.backend.util.strategy.QuickSort;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoControler {
    // EXEMPLO DE PLAYLIST PARAM -> PLj6Dc5_-lwbnYUOlbQ4loX1ekdIkM_7To

    private RestClient client;
    private String token = "AIzaSyAU3UFsDrG_9wFK5UvgNjjfn1Qk_JBx9pg";

    public VideoControler() {
        client = RestClient.builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build();
    }

    @Operation(summary = "Este endpoint busca os vídeos da playlist")
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

    @GetMapping("/csv/{id}")
    public ResponseEntity<List<Video>> exportCsv(@PathVariable String id){
        String url = ("playlistItems?key=%s&part=snippet&playlistId=%s").formatted(token, id);

        PlaylistApiExternaDto raw = client.get()
                .uri(url)
                .retrieve()
                .body(PlaylistApiExternaDto.class);
        System.out.println("Dados retornados da API externa: " + raw);
        System.out.println("Itens retornados: " + raw.getItems());
        System.out.println("IDs dos vídeos: " + raw.getVideoIds());
        System.out.println("Títulos: " + raw.getTitle());


        ListaObj<Video> videos = new ListaObj(raw.getItems().size());
//        Video[] videos = new Video[raw.getItems().size()];
        List<Video> videoList = new ArrayList<>();
        for (int i = 0; i < raw.getItems().size(); i++) {
            Video video = new Video();
            video.setId(raw.getVideoIds().get(i));
            video.setTitulo(raw.getTitle().get(i));
            video.setViews(buscarViewsVideo(raw.getVideoIds().get(i))); // Obtém views por ID
            videoList.add(video); // Adiciona à lista principal
            videos.adiciona(video); // Adiciona à ListaObj
        }


        IOrdenacao<Video> quickSort = new QuickSort<>();
        Comparator<Video> comparador = Comparator.comparing(Video::getViews);
        quickSort.ordenar(videoList, comparador);

        IO export = new IO();
        export.export(videoList);
        return videoList == null ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(videoList);
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
