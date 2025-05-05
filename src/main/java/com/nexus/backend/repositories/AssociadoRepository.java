package com.nexus.backend.repositories;

import com.nexus.backend.entities.Associado;
import com.nexus.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssociadoRepository extends JpaRepository<Associado, Integer> {
        Optional<Usuario> findByEmail(String email);

    List<Associado> findByAprovadoFalse();

    List<Associado> findByAprovadoTrue();
}
