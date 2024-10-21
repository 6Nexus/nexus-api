package com.nexus.backend.repositories;

import com.nexus.backend.entities.Professor;
import com.nexus.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    Optional<Usuario> findByEmail(String email);

}
