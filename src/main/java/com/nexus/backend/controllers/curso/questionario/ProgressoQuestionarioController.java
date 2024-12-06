package com.nexus.backend.controllers.curso.questionario;

import com.nexus.backend.dto.curso.curso.CursoCriacaoDto;
import com.nexus.backend.dto.curso.curso.CursoRespostaDto;
import com.nexus.backend.dto.curso.questionario.progresso.ProgressoQuestionarioCriacaoDto;
import com.nexus.backend.dto.curso.questionario.progresso.ProgressoQuestionarioRespostaDto;
import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.entities.curso.questionario.ProgressoQuestionario;
import com.nexus.backend.mappers.curso.CursoMapper;
import com.nexus.backend.mappers.curso.questionario.progresso.ProgressoQuestionarioMapper;
import com.nexus.backend.service.curso.questionario.ProgressoQuestionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progresso-questionarios")
@RequiredArgsConstructor
public class ProgressoQuestionarioController {
    private final ProgressoQuestionarioService progressoQuestionarioService;

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody @Valid ProgressoQuestionarioCriacaoDto progressoQuestionarioCriacaoDto) {
        ProgressoQuestionario progressoEntrada = ProgressoQuestionarioMapper.toEntidade(progressoQuestionarioCriacaoDto);
        Integer idCursoSalvo = progressoQuestionarioService.cadastrar(progressoEntrada, progressoQuestionarioCriacaoDto.getMatriculaId(), progressoQuestionarioCriacaoDto.getQuestionarioId());

        return ResponseEntity.created(null).body(idCursoSalvo);
    }

    @GetMapping("{matriculaId}/{questionarioId}")
    public ResponseEntity<ProgressoQuestionarioRespostaDto> buscarPorMatriculaEQuestionario(
            @PathVariable Integer matriculaId,
            @PathVariable Integer questionarioId) {
        ProgressoQuestionario progressoEncontrado = progressoQuestionarioService.buscarPorMatriculaEQuestionario(matriculaId, questionarioId);
        ProgressoQuestionarioRespostaDto progressoEncontradoDto = ProgressoQuestionarioMapper.toRespostaDto(progressoEncontrado);
        return ResponseEntity.ok(progressoEncontradoDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProgressoQuestionarioRespostaDto> buscarPorId(
            @PathVariable Integer id) {
        ProgressoQuestionario progressoEncontrado = progressoQuestionarioService.buscarPorId(id);
        ProgressoQuestionarioRespostaDto progressoEncontradoDto = ProgressoQuestionarioMapper.toRespostaDto(progressoEncontrado);
        return ResponseEntity.ok(progressoEncontradoDto);
    }

    @PatchMapping("/pontuacao/{idProgresso}/{novaPontuacao}")
    public ResponseEntity<Double> atualizarPontuacao(
            @PathVariable Integer idProgresso,
            @PathVariable Double novaPontuacao) {
        Double pontuacaoAlterada = progressoQuestionarioService.atualizarPontuacao(idProgresso, novaPontuacao);

        return ResponseEntity.ok(pontuacaoAlterada);
    }
}
