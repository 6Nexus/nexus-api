package com.nexus.backend.controllers.curso.video;

import com.nexus.backend.dto.curso.questionario.progresso.ProgressoQuestionarioRespostaDto;
import com.nexus.backend.dto.curso.video.progresso.ProgressoVideoCriacaoDto;
import com.nexus.backend.dto.curso.video.progresso.ProgressoVideoRespostaDto;
import com.nexus.backend.entities.curso.questionario.ProgressoQuestionario;
import com.nexus.backend.entities.curso.video.ProgressoVideo;
import com.nexus.backend.mappers.curso.questionario.progresso.ProgressoQuestionarioMapper;
import com.nexus.backend.mappers.curso.video.ProgressoVideoMapper;
import com.nexus.backend.service.curso.video.ProgressoVideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progresso-video")
@RequiredArgsConstructor
public class ProgressoVideoController {
    private final ProgressoVideoService progressoVideoService;

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody @Valid ProgressoVideoCriacaoDto progressoVideoCriacaoDto) {
        ProgressoVideo progressoEntrada = ProgressoVideoMapper.toEntidade();
        Integer idCursoSalvo = progressoVideoService.cadastrar(progressoEntrada, progressoVideoCriacaoDto.getMatriculaId(), progressoVideoCriacaoDto.getVideoId());

        return ResponseEntity.created(null).body(idCursoSalvo);
    }

    @GetMapping("{matriculaId}/{videoId}")
    public ResponseEntity<ProgressoVideoRespostaDto> buscarPorMatriculaEVideo(
            @PathVariable Integer matriculaId,
            @PathVariable Integer videoId) {
        ProgressoVideo progressoEncontrado = progressoVideoService.buscarPorMatriculaEVideo(matriculaId, videoId);
        ProgressoVideoRespostaDto progressoReposta = ProgressoVideoMapper.toResposta(progressoEncontrado);
        return ResponseEntity.ok(progressoReposta);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProgressoVideoRespostaDto> buscarPorId(@PathVariable Integer id) {
        ProgressoVideo progressoEncontrado = progressoVideoService.buscarPorId(id);
        ProgressoVideoRespostaDto progressoReposta = ProgressoVideoMapper.toResposta(progressoEncontrado);
        return ResponseEntity.ok(progressoReposta);
    }

    @PatchMapping("/assistido/{progressoId}")
    public ResponseEntity<ProgressoVideoRespostaDto> atualizarAssistido(@PathVariable Integer progressoId) {
        ProgressoVideo progressoAlterado = progressoVideoService.atualizarAssistido(progressoId);
        ProgressoVideoRespostaDto progressoReposta = ProgressoVideoMapper.toResposta(progressoAlterado);
        return ResponseEntity.ok(progressoReposta);
    }
}
