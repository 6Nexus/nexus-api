package com.nexus.backend.repositories.questionario;

import com.nexus.backend.entities.questionario.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionarioRepository extends JpaRepository<Questionario, Integer> {
}
