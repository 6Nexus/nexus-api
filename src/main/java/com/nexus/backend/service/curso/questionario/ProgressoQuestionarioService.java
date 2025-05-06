package com.nexus.backend.service.curso.questionario;

import com.nexus.backend.entities.curso.questionario.ProgressoQuestionario;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.questionario.ProgressoQuestionarioRepository;
import com.nexus.backend.service.curso.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgressoQuestionarioService {
    private final ProgressoQuestionarioRepository progressoQuestionarioRepository;
    private final MatriculaService matriculaService;
    private final QuestionarioService questionarioService;

    public Integer cadastrar(ProgressoQuestionario progressoQuestionarioACadastrar, Integer matriculaId, Integer questionarioId) {
        progressoQuestionarioACadastrar.setDataAtualizacao(LocalDateTime.now());
        progressoQuestionarioACadastrar.setMatricula(matriculaService.buscarPorId(matriculaId));
        progressoQuestionarioACadastrar.setQuestionario(questionarioService.buscarPorId(questionarioId));

        return progressoQuestionarioRepository.save(progressoQuestionarioACadastrar).getId();
    }

    public ProgressoQuestionario buscarPorId(Integer progressoId) {
        return progressoQuestionarioRepository.findById(progressoId).orElseThrow(
                () -> new EntityNotFoundException("Progresso Questionário")
        );
    }

    public Double atualizarPontuacao(Integer progressoId, Double novaPontuacao) {
        ProgressoQuestionario progressoCadastrado = buscarPorId(progressoId);
        progressoCadastrado.setDataAtualizacao(LocalDateTime.now());
        progressoCadastrado.setPontuacao(novaPontuacao);

        return progressoQuestionarioRepository.save(progressoCadastrado).getPontuacao();
    }

    public ProgressoQuestionario buscarPorMatriculaEQuestionario(Integer matriculaId, Integer questionarioId) {
        return progressoQuestionarioRepository.findByMatriculaIdAndQuestionarioId(matriculaId, questionarioId).orElseThrow(
                () -> new EntityNotFoundException("Progresso Questionário")
        );
    }

    public Integer quantidadePorMatriculaComPontuacaoMaiorQue70(Integer matriculaId) {
        return progressoQuestionarioRepository.countByMatriculaIdAndPontuacaoGreaterThan(matriculaId, 70.0).orElseThrow(
                () -> new EntityNotFoundException("Progresso Questionário")
        );    }

    public List<ProgressoQuestionario> respondidosOntemComPontuacaoMaiorQue70() {
        LocalDate ontem = LocalDate.now();
        LocalDateTime inicioDeOntem = ontem.atStartOfDay();
        LocalDateTime fimDeOntem = ontem.atTime(LocalTime.MAX);

        return progressoQuestionarioRepository.findByDataAtualizacaoBetweenAndPontuacaoGreaterThanEqual(inicioDeOntem, fimDeOntem,70.0);
    }
}
