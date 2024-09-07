package com.nexus.backend.controllers;

import com.nexus.backend.entities.Usuario;
import com.nexus.backend.repositories.UsuarioRepository;
import com.nexus.backend.dto.CursoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Cadastrar usuário
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario novoUsuario) {
        novoUsuario.setId(null); // Garantir que seja um novo usuário
        return ResponseEntity.status(201).body(usuarioRepository.save(novoUsuario));
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    // Buscar usuário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable int id) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);
        if (usuarioOp.isPresent()) {
            return ResponseEntity.status(200).body(usuarioOp.get());
        }
        return ResponseEntity.status(404).build();
    }

    // Atualizar usuário pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable int id, @RequestBody Usuario atualizarUsuario) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(404).build();
        }
        atualizarUsuario.setId(id);
        Usuario usuarioAtualizado = usuarioRepository.save(atualizarUsuario);
        return ResponseEntity.status(200).body(usuarioAtualizado);
    }

    // Deletar usuário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPorId(@PathVariable int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    // Novo método: Visualizar dados de curso com base no tipo de usuário (utilizando Strategy)
    @GetMapping("/{id}/curso/{cursoId}")
    public ResponseEntity<CursoDto> visualizarDadosCurso(@PathVariable int id, @PathVariable int cursoId) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);

        if (usuarioOp.isPresent()) {
            Usuario usuario = usuarioOp.get();
            CursoDto cursoDto = usuario.visualizarCurso(cursoId); // Aplica a estratégia de visualização
            return ResponseEntity.status(200).body(cursoDto);
        }

        return ResponseEntity.status(404).build();
    }
}
