package com.nexus.backend.controllers;

import com.nexus.backend.entities.Professor;
import com.nexus.backend.repositories.ProfessorRepository;
import com.nexus.backend.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    // Cadastrar professor
    @PostMapping
    public ResponseEntity<Professor> cadastrarProfessor(@RequestBody @Valid Professor novoProfessor) {
        return ResponseEntity.created(null).body(professorService.register(novoProfessor));
    }

    // Listar todos os professores
    @GetMapping
    public ResponseEntity<List<Professor>> listarProfessores() {
        List<Professor> professores = professorService.getAll();
        if (professores.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(professores);
    }

    // Buscar professor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(professorService.getById(id));
    }

    // Atualizar professor
    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable int id, @RequestBody @Valid Professor professorAtualizado) {
        return ResponseEntity.ok(professorService.update(id, professorAtualizado));
    }

    // Deletar professor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable int id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
