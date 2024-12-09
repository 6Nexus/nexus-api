package com.nexus.backend.repositories;

import com.nexus.backend.entities.Administrador;
import com.nexus.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
    Optional<Usuario> findByEmail(String email);
}
