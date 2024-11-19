package com.nexus.backend.controllers;

import com.nexus.backend.dto.administrador.AdministradorCriacaoDto;
import com.nexus.backend.dto.administrador.AdministradorRespostaDto;
import com.nexus.backend.dto.professor.ProfessorRespostaDto;
import com.nexus.backend.dto.usuario.UsuarioLoginDto;
import com.nexus.backend.dto.usuario.UsuarioTokenDto;
import com.nexus.backend.entities.Administrador;
import com.nexus.backend.entities.Professor;
import com.nexus.backend.mappers.AdministradorMapper;
import com.nexus.backend.mappers.ProfessorMapper;
import com.nexus.backend.service.AdministradorService;
import com.nexus.backend.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
@RequiredArgsConstructor
public class AdministradorController {
    private final AdministradorService administradorService;
    private final AdministradorMapper administradorMapper;
    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;

    @PostMapping("/login")
    public ResponseEntity <UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){
        UsuarioTokenDto usuarioTokenDto = administradorService.autenticar(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @PostMapping
    public ResponseEntity<AdministradorRespostaDto> cadastro (@RequestBody @Valid AdministradorCriacaoDto admin){
        Administrador entity = administradorMapper.toCriacaoEntity(admin);

        Administrador adminSalvo = administradorService.register(entity);

        return ResponseEntity.created(null).body(administradorMapper.toRespostaDto(adminSalvo));
    }

    @GetMapping
    public ResponseEntity<List<AdministradorRespostaDto>> listarAdmin(){
        List<Administrador> admins = administradorService.getAll();
        if (admins.isEmpty()) return ResponseEntity.noContent().build();
        List<AdministradorRespostaDto> administradorRespostaDtoList = admins
                .stream()
                .map(administradorMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(administradorRespostaDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministradorRespostaDto> attAdmin(
            @PathVariable int id,
            @RequestBody @Valid AdministradorCriacaoDto admin){
        Administrador entity = administradorMapper.toCriacaoEntity(admin);
        Administrador adminSalvo = administradorService.update(id,entity);
        AdministradorRespostaDto respostaDto = administradorMapper.toRespostaDto(adminSalvo);

        return ResponseEntity.created(null).body(respostaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssociado (@PathVariable int id){
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // gerenciamento de professores
    // Deletar professor
    @Operation(summary = "Este endpoint deleta um professor do sistema")
    @DeleteMapping("/professor/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable int id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/professor/aprovadoFalse")
    public ResponseEntity<List<ProfessorRespostaDto>> getProfAprovadoFalse(){
        List<Professor> professores = professorService.getAprovadoFalse();
        if (professores.isEmpty()) return ResponseEntity.noContent().build();

        List<ProfessorRespostaDto> professorRespostaDtos = professores
                .stream()
                .map(professorMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(professorRespostaDtos);
    }

    @GetMapping("/professor/aprovadoTrue")
    public ResponseEntity<List<ProfessorRespostaDto>> getProfAprovadoTrue(){
        List<Professor> professores = professorService.getAprovadoTrue();
        if (professores.isEmpty()) return ResponseEntity.noContent().build();

        List<ProfessorRespostaDto> professorRespostaDtos = professores
                .stream()
                .map(professorMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(professorRespostaDtos);
    }
}
