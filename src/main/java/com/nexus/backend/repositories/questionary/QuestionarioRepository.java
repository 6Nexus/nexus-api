package com.nexus.backend.repositories.questionary;

import com.nexus.backend.entities.questionary.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionarioRepository extends JpaRepository<Questionario, Integer> {
}
