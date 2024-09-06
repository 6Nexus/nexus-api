package com.nexus.backend.controllers;

import com.nexus.backend.entities.Usuario;
import com.nexus.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    // cadastrar usuario
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario novoUsuario){
        novoUsuario.setId(null);
        return ResponseEntity.status(201).body(usuarioRepository.save(novoUsuario));
    }

    // listar todos os usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> livros = usuarioRepository.findAll();
        if(livros.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(livros);
    }

    // buscar usuario pelo Id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable int id){
        Optional<Usuario> livroOp = usuarioRepository.findById(id);

        if(livroOp.isPresent()){
            Usuario usuarioEncontrado = livroOp.get();
            return ResponseEntity.status(200).body(usuarioEncontrado);
        }

        return ResponseEntity.status(404).build();
    }

    // atualizar usuario pelo Id
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuarios(@PathVariable int id, @RequestBody Usuario atualizarUsuario){
        if(!usuarioRepository.existsById(id)){
            return ResponseEntity.status(404).build();
        }
        atualizarUsuario.setId(id);
        Usuario usuarioRetorno = usuarioRepository.save(atualizarUsuario);
        return ResponseEntity.status(200).body(usuarioRetorno);
    }

    // Deletar usuario pelo Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPorId(@PathVariable int id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
