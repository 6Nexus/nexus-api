package com.nexus.backend.repositories.questionario;

import com.nexus.backend.entities.questionario.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
    Optional<Pergunta> findByQuestionarioId(Integer id);
}
