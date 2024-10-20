package com.nexus.backend.repositories.questionary;

import com.nexus.backend.entities.questionary.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
}
