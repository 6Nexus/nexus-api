package com.nexus.backend.repositories.curso;

import com.nexus.backend.entities.curso.Curtida;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurtidaRepository extends JpaRepository<Curtida, Integer> {
    List<Curtida> findByAssociadoId(Integer idAssociado);

    Optional<Curtida> findByAssociadoIdAndCursoId(Integer idAssociado, Integer idCurso);

    boolean existsByAssociadoIdAndCursoId(Integer idAssociado, Integer idCurso);

    void deleteByAssociadoIdAndCursoId(Integer idAssociado, Integer idCurso);
}
