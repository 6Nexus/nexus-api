package com.nexus.backend.controllers;

import com.nexus.backend.entities.Professor;
import com.nexus.backend.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    // Cadastrar professor
    @PostMapping
    public ResponseEntity<Professor> cadastrarProfessor(@RequestBody Professor novoProfessor){
        novoProfessor.setId(null);
        return ResponseEntity.status(201).body(professorRepository.save(novoProfessor));
    }

    // Listar todos os professores
    @GetMapping
    public ResponseEntity<List<Professor>> listarProfessores() {
        List<Professor> professores = professorRepository.findAll();
        if(professores.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(professores);
    }

    // Buscar professor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable int id){
        Optional<Professor> professorOp = professorRepository.findById(id);

        if(professorOp.isPresent()){
            Professor professorEncontrado = professorOp.get();
            return ResponseEntity.status(200).body(professorEncontrado);
        }

        return ResponseEntity.status(404).build();
    }

    // Atualizar professor
    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable int id, @RequestBody Professor professorAtualizado){
        if(!professorRepository.existsById(id)){
            return ResponseEntity.status(404).build();
        }
        professorAtualizado.setId(id);
        Professor professorSalvo = professorRepository.save(professorAtualizado);
        return ResponseEntity.status(200).body(professorSalvo);
    }

    // Deletar professor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable int id){
        if(professorRepository.existsById(id)){
            professorRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
