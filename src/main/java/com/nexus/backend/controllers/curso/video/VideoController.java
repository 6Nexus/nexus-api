package com.nexus.backend.controllers.curso.video;

import com.nexus.backend.dto.curso.curso.CursoCriacaoDto;
import com.nexus.backend.dto.curso.modulo.ModuloRespostaDto;
import com.nexus.backend.dto.curso.video.VideoCriacaoDto;
import com.nexus.backend.dto.curso.video.VideoRespostaDto;
import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.entities.curso.Modulo;
import com.nexus.backend.entities.curso.video.Video;
import com.nexus.backend.mappers.curso.CursoMapper;
import com.nexus.backend.mappers.curso.ModuloMapper;
import com.nexus.backend.mappers.curso.video.VideoMapper;
import com.nexus.backend.service.curso.CursoService;
import com.nexus.backend.service.curso.video.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody @Valid VideoCriacaoDto videoCriacaoDto) {
        Video videoEntrada = VideoMapper.toEntidade(videoCriacaoDto);
        Integer idVideoSalvo = videoService.cadastrar(videoEntrada, videoCriacaoDto.getIdModulo());

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

    @DeleteMapping("/{idVideo}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idVideo) {
        videoService.deletar(idVideo);
        return ResponseEntity.noContent().build();
    }


}
