package com.nexus.backend.controllers.curso.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.backend.dto.curso.video.video.VideoCriacaoDto;
import com.nexus.backend.dto.curso.video.video.VideoRespostaDto;
import com.nexus.backend.entities.curso.video.Video;
import com.nexus.backend.mappers.curso.video.VideoMapper;
import com.nexus.backend.service.curso.video.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

//  upload de video no servidor
    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestParam("json") String json,
                                             @RequestParam("arquivo") MultipartFile file) {
        Integer idVideoSalvo = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            VideoCriacaoDto videoGeradoDoJson = objectMapper.readValue(json, VideoCriacaoDto.class);

            Video videoEntrada = VideoMapper.toEntidade(videoGeradoDoJson);

            idVideoSalvo = videoService.cadastrar(videoEntrada, videoGeradoDoJson.getIdModulo(), file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.created(null).body(idVideoSalvo);
    }

    @GetMapping("/modulo/{idModulo}")
    public ResponseEntity<List<VideoRespostaDto>> listarPorModulo(@PathVariable Integer idModulo) {
        List<Video> videosEncontrados = videoService.listarPorModulo(idModulo);
        if (videosEncontrados.isEmpty()) return ResponseEntity.noContent().build();

        List<VideoRespostaDto> videosMapeados = videosEncontrados.stream()
                .map(VideoMapper::toRespostaDto)
                .toList();

        return ResponseEntity.ok(videosMapeados);
    }

    @PostMapping("/force-cron")
    public ResponseEntity<Void> rodarCron() {
        videoService.cronCarregamentoYoutube();
        return ResponseEntity.noContent().build();
    }
}
