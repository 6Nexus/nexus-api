package com.nexus.backend.repositories.curso;

import com.nexus.backend.entities.curso.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuloRepository extends JpaRepository<Modulo, Integer> {
    List<Modulo> findAllByCursoIdOrderByOrdemAsc(Integer cursoId);
}
