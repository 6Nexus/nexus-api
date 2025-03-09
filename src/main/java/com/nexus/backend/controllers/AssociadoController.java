package com.nexus.backend.controllers;

import com.nexus.backend.dto.associado.AssociadoAtualizacaoDto;
import com.nexus.backend.dto.associado.AssociadoCriacaoDto;
import com.nexus.backend.dto.associado.AssociadoRespostaDto;
import com.nexus.backend.dto.usuario.UsuarioLoginDto;
import com.nexus.backend.dto.usuario.UsuarioTokenDto;
import com.nexus.backend.entities.Associado;
import com.nexus.backend.mappers.AssociadoMapper;
import com.nexus.backend.service.AssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/associados")
@RequiredArgsConstructor
public class AssociadoController {

    private final AssociadoService associadoService;
    private final AssociadoMapper associadoMapper;
    private final PasswordEncoder passwordEncoder;

    // Login com JWT
    @Operation(summary = "Este endpoint autentica os usuários com credenciais válidas.")
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioTokenDto = associadoService.autenticar(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    // Cadastrar associado
    @Operation(summary = "Este endpoint permite a criação de um novo associado no sistema.")
    @PostMapping
    public ResponseEntity<AssociadoRespostaDto> cadastrarAssociado(@RequestBody @Valid AssociadoCriacaoDto a) {
        Associado entity = associadoMapper.toCriacaoEntity(a);
        Associado associadoSalvo = associadoService.register(entity);
        return ResponseEntity.created(null).body(associadoMapper.toRespostaDto(associadoSalvo));
    }

    // Listar todos os associados
    @Operation(summary = "Este endpoint permite listar todos os usuários do sistema")
    @GetMapping
    public ResponseEntity<List<AssociadoRespostaDto>> listarAssociados() {
        List<Associado> associados = associadoService.getAll();
        if (associados.isEmpty()) return ResponseEntity.noContent().build();
        List<AssociadoRespostaDto> associadoRespostaDtoList = associados
                .stream()
                .map(associadoMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(associadoRespostaDtoList);
    }

    // Buscar associado por ID
    @Operation(summary = "Este endpoint permite buscar um usuário específico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<AssociadoRespostaDto> buscarPorId(@PathVariable int id) {
        Associado a = associadoService.getById(id);
        AssociadoRespostaDto dto = associadoMapper.toRespostaDto(a);
        return ResponseEntity.ok(dto);
    }

    // Atualizar associado
    @Operation(summary = "Este endpoint permite atualizar informações de um usuário específico do sistema.")
    @PutMapping("/{id}")
    public ResponseEntity<AssociadoRespostaDto> atualizarAssociado(@PathVariable int id, @RequestBody @Valid AssociadoAtualizacaoDto a) {
        Associado associadoEncontrado = associadoService.getById(id);
        Associado entity = associadoMapper.toAtualizacaoEntity(a, associadoEncontrado, passwordEncoder);
        Associado associadoSalvo = associadoService.update(id, entity);
        AssociadoRespostaDto returnDto = associadoMapper.toRespostaDto(associadoSalvo);
        return ResponseEntity.created(null).body(returnDto);
    }

    // Deletar associado - Admin
    @Operation(summary = "Este endpoint permite deletar o usuário do sistema através do admin")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deletarAssociadoAdmin(@PathVariable int id) {
        associadoService.adminDelete(id);
        return ResponseEntity.noContent().build();
    }

    // Deletar associado - Associado
    @Operation(summary = "Este endpoint permite que um usuário delete a própria conta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssociado(@PathVariable int id, @RequestBody UsuarioLoginDto usuarioLoginDto) {
        associadoService.delete(id, usuarioLoginDto);
        return ResponseEntity.noContent().build();
    }
}
