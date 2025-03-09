package com.nexus.backend.controllers;

import com.nexus.backend.dto.professor.ProfessorCriacaoDto;
import com.nexus.backend.dto.professor.ProfessorRespostaDto;
import com.nexus.backend.dto.usuario.UsuarioLoginDto;
import com.nexus.backend.dto.usuario.UsuarioTokenDto;
import com.nexus.backend.entities.Professor;
import com.nexus.backend.mappers.ProfessorMapper;
import com.nexus.backend.repositories.ProfessorRepository;
import com.nexus.backend.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Este endpoint autentica os usuários com credenciais válidas.")
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioTokenDto = professorService.autenticar(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @Operation(summary = "Este endpoint permite a criação de um novo professor no sistema.")
    @PostMapping
    public ResponseEntity<ProfessorRespostaDto> cadastrarProfessor(@RequestBody @Valid ProfessorCriacaoDto p) {
        Professor entity = professorMapper.toCriacaoEntity(p);
        Professor pSalvo = professorService.register(entity);
        return ResponseEntity.created(null).body(professorMapper.toRespostaDto(pSalvo));
    }

    @Operation(summary = "Este endpoint lista todos os professores")
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
    @Operation(summary = "Este endpoint busca o professor pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorRespostaDto> buscarPorId(@PathVariable int id) {
        Professor p = professorService.getById(id);
        ProfessorRespostaDto dto = professorMapper.toRespostaDto(p);
        return ResponseEntity.ok(dto);
    }

    // Atualizar professor
    @Operation(summary = "Este endpoint atualiza os dados do professor")
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorRespostaDto> atualizarProfessor(@PathVariable int id, @RequestBody @Valid ProfessorCriacaoDto p) {
        Professor entity = professorMapper.toCriacaoEntity(p);
        Professor pSalvo = professorService.update(id, entity);
        return ResponseEntity.created(null).body(professorMapper.toRespostaDto(pSalvo));
    }


}
