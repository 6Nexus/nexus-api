package com.nexus.backend.controllers;

import com.nexus.backend.entities.Associado;
import com.nexus.backend.repositories.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/associados")
public class AssociadoController {

    @Autowired
    private AssociadoRepository associadoRepository;

    // Cadastrar associado
    @PostMapping
    public ResponseEntity<Associado> cadastrarAssociado(@RequestBody Associado novoAssociado){
        novoAssociado.setId(null);
        return ResponseEntity.status(201).body(associadoRepository.save(novoAssociado));
    }

    // Listar todos os associados
    @GetMapping
    public ResponseEntity<List<Associado>> listarAssociados() {
        List<Associado> associados = associadoRepository.findAll();
        if(associados.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(associados);
    }

    // Buscar associado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Associado> buscarPorId(@PathVariable int id){
        Optional<Associado> associadoOp = associadoRepository.findById(id);

        if(associadoOp.isPresent()){
            Associado associadoEncontrado = associadoOp.get();
            return ResponseEntity.status(200).body(associadoEncontrado);
        }

        return ResponseEntity.status(404).build();
    }

    // Atualizar associado
    @PutMapping("/{id}")
    public ResponseEntity<Associado> atualizarAssociado(@PathVariable int id, @RequestBody Associado associadoAtualizado){
        if(!associadoRepository.existsById(id)){
            return ResponseEntity.status(404).build();
        }
        associadoAtualizado.setId(id);
        Associado associadoSalvo = associadoRepository.save(associadoAtualizado);
        return ResponseEntity.status(200).body(associadoSalvo);
    }

    // Deletar associado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssociado(@PathVariable int id){
        if(associadoRepository.existsById(id)){
            associadoRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
