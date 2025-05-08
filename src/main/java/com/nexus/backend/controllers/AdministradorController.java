package com.nexus.backend.controllers;

import com.nexus.backend.dto.administrador.AdministradorCriacaoDto;
import com.nexus.backend.dto.administrador.AdministradorRespostaDto;
import com.nexus.backend.dto.associado.AssociadoRespostaDto;
import com.nexus.backend.dto.professor.ProfessorRespostaDto;
import com.nexus.backend.dto.usuario.UsuarioLoginDto;
import com.nexus.backend.dto.usuario.UsuarioTokenDto;
import com.nexus.backend.entities.Administrador;
import com.nexus.backend.entities.Associado;
import com.nexus.backend.entities.Professor;
import com.nexus.backend.mappers.AdministradorMapper;
import com.nexus.backend.mappers.AssociadoMapper;
import com.nexus.backend.mappers.ProfessorMapper;
import com.nexus.backend.service.AdministradorService;
import com.nexus.backend.service.AssociadoService;
import com.nexus.backend.service.ProfessorService;
import org.springframework.core.io.Resource;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/administradores")
//@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AdministradorController {
    private final AdministradorService administradorService;
    private final AdministradorMapper administradorMapper;
    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;
    private final AssociadoService associadoService;
    private final AssociadoMapper associadoMapper;

    private static final String CSV_FILE_PATH = "./relatorio-videos.csv";
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
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

    @GetMapping(value = "/csv", produces = "text/csv")
    public ResponseEntity<Resource> downloadCsv() throws IOException {
        Resource fileResource = new FileSystemResource(CSV_FILE_PATH);

        if (!fileResource.exists()) {
            throw new FileNotFoundException("Arquivo CSV não encontrado em: " + CSV_FILE_PATH);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"relatorio-videos.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(fileResource);
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

    @PutMapping("/professor/aprovar/{id}")
    public ResponseEntity<ProfessorRespostaDto> setProfessorAprovado(@PathVariable Integer id){
        Professor p = professorService.setAprovado(id);
        return ResponseEntity.created(null).body(professorMapper.toRespostaDto(p));
    }

    @PutMapping("/professor/bloquear/{id}")
    public ResponseEntity<ProfessorRespostaDto> bloquear(@PathVariable Integer id){
        Professor p = professorService.bloquear(id);
        return ResponseEntity.created(null).body(professorMapper.toRespostaDto(p));
    }

    // gerenciamento de associadas

    // Deletar associados
    @Operation(summary = "Este endpoint permite deletar o usuário do sistema através do admin")
    @DeleteMapping("/associados/{id}")
    public ResponseEntity<Void> deletarAssociadoAdmin(@PathVariable int id) {
        associadoService.adminDelete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/associados/aprovadoFalse")
    public ResponseEntity<List<AssociadoRespostaDto>> getAssociadoAprovadoFalse(){
        List<Associado> associados = associadoService.getAprovadoFalse();
        if (associados.isEmpty()) return ResponseEntity.noContent().build();

        List<AssociadoRespostaDto> associadoRespostaDtos = associados
                .stream()
                .map(associadoMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(associadoRespostaDtos);
    }

    @GetMapping("/associados/aprovadoTrue")
    public ResponseEntity<List<AssociadoRespostaDto>> getAssociadoAprovadoTrue(){
        List<Associado> associados = associadoService.getAprovadoTrue();
        if (associados.isEmpty()) return ResponseEntity.noContent().build();

        List<AssociadoRespostaDto> associadoRespostaDtos = associados
                .stream()
                .map(associadoMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(associadoRespostaDtos);
    }

    @PutMapping("/associados/aprovar/{id}")
    public ResponseEntity<AssociadoRespostaDto> setAssociadoAprovado(@PathVariable Integer id){
        Associado a = associadoService.setAprovado(id);
        return ResponseEntity.created(null).body(associadoMapper.toRespostaDto(a));
    }

    @PutMapping("/associados/bloquear/{id}")
    public ResponseEntity<AssociadoRespostaDto> bloquearAssociado(@PathVariable Integer id){
        Associado a = associadoService.bloquear(id);
        return ResponseEntity.created(null).body(associadoMapper.toRespostaDto(a));
    }


}
