package com.nexus.backend.repositories.curso.questionario;

import com.nexus.backend.entities.curso.questionario.ProgressoQuestionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressoQuestionarioRepository extends JpaRepository<ProgressoQuestionario, Integer> {
    Optional<ProgressoQuestionario> findByMatriculaIdAndQuestionarioId(Integer matriculaId, Integer questionarioId);

    Optional<Integer> countByMatriculaIdAndPontuacaoGreaterThan(Integer matriculaId, Double pontuacao);

    List<ProgressoQuestionario> findByMatriculaId(Integer matriculaId);
}
