package com.nexus.backend.repositories.curso.questionario;

import com.nexus.backend.entities.curso.questionario.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionarioRepository extends JpaRepository<Questionario, Integer> {
    Optional<Questionario> findByModuloId(Integer idModulo);
}
