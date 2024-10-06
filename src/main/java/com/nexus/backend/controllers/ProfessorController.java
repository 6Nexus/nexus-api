package com.nexus.backend.controllers;

import com.nexus.backend.dto.professor.ProfessorCriacaoDto;
import com.nexus.backend.dto.professor.ProfessorRespostaDto;
import com.nexus.backend.entities.Professor;
import com.nexus.backend.mappers.ProfessorMapper;
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
    private final ProfessorMapper professorMapper;

    // Cadastrar professor
    @PostMapping
    public ResponseEntity<ProfessorRespostaDto> cadastrarProfessor(@RequestBody @Valid ProfessorCriacaoDto p) {
        Professor entity = professorMapper.toCriacaoEntity(p);
        Professor pSalvo = professorService.register(entity);
        return ResponseEntity.created(null).body(professorMapper.toRespostaDto(pSalvo));
    }

    // Listar todos os professores
    @GetMapping
    public ResponseEntity<List<ProfessorRespostaDto>> listarProfessores() {
        List<Professor> professores = professorService.getAll();
        if (professores.isEmpty()) return ResponseEntity.noContent().build();

        List<ProfessorRespostaDto> professorRespostaDtos = professores
                .stream()
                .map(professorMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(professorRespostaDtos);
    }

    // Buscar professor por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorRespostaDto> buscarPorId(@PathVariable int id) {
        Professor p = professorService.getById(id);
        ProfessorRespostaDto dto = professorMapper.toRespostaDto(p);
        return ResponseEntity.ok(dto);
    }

    // Atualizar professor
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorRespostaDto> atualizarProfessor(@PathVariable int id, @RequestBody @Valid ProfessorCriacaoDto p) {
        Professor entity = professorMapper.toCriacaoEntity(p);
        Professor pSalvo = professorService.update(id, entity);
        return ResponseEntity.created(null).body(professorMapper.toRespostaDto(pSalvo));
    }

    // Deletar professor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable int id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
