package com.nexus.backend.repositories.questionario;

import com.nexus.backend.entities.questionario.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Integer> {
    List<Resposta> findAllByPerguntaId(Integer perguntaId);
}
