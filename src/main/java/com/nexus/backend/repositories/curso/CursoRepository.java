package com.nexus.backend.repositories.curso;

import com.nexus.backend.entities.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
    List<Curso> findAllByCategoria(String categoria);

    List<Curso> findAllByProfessorId(Integer professorId);
}
