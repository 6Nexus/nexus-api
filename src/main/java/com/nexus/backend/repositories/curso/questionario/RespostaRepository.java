package com.nexus.backend.repositories.curso.questionario;

import com.nexus.backend.entities.curso.questionario.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Integer> {

    @Query("SELECT r FROM Resposta r WHERE r.pergunta.id IN :ids")
    List<Resposta> buscarPorIds(List<Integer> ids);
}
