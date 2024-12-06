package com.nexus.backend.repositories.curso;

import com.nexus.backend.entities.curso.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {

    List<Matricula> findAllByAssociadoId(Integer idAssociado);

    Optional<Matricula> findByAssociadoIdAndCursoId(Integer idAssociado, Integer idCurso);
}
