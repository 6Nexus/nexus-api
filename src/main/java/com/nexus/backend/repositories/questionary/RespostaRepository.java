package com.nexus.backend.repositories.questionary;

import com.nexus.backend.entities.questionary.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Integer> {
    List<Resposta> findAllByPerguntaId(Integer perguntaId);
}
