package com.nexus.backend.controllers;

import com.nexus.backend.entities.Desaparecido;
import com.nexus.backend.repositories.DesaparecidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/desaparecidos")
public class DesaparecidoController {
    @Autowired
    DesaparecidoRepository desaparecidoRepository;

    @GetMapping
    public ResponseEntity<List<Desaparecido>> getAll(){
        List<Desaparecido> desaparecidos = desaparecidoRepository.findAll();

        if (desaparecidos.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(desaparecidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Desaparecido> getById(@PathVariable Integer id){
        Optional<Desaparecido> desaparecidoOpt = desaparecidoRepository.findById(id);

        if (desaparecidoOpt.isPresent()) return ResponseEntity.status(200).body(desaparecidoOpt.get());

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/dataOcorrencia")
    public ResponseEntity<List<Desaparecido>> getDataOcorrencia(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        if (inicio.isAfter(fim)) return ResponseEntity.status(400).build();

        List<Desaparecido> desaparecidos = desaparecidoRepository.findByDataOcorrenciaBetween(inicio,fim);

        if (desaparecidos.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(desaparecidos);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Desaparecido>> getByNome(@RequestParam String nome){
        List<Desaparecido> desaparecidos = desaparecidoRepository.findByNomeContainsIgnoreCase(nome);

        if (desaparecidos.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(desaparecidos);
    }

    @PostMapping
    public ResponseEntity<Desaparecido> register(@RequestBody Desaparecido d){
        d.setId(null);
        Desaparecido novoResgistro =desaparecidoRepository.save(d);
        return ResponseEntity.status(201).body(novoResgistro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Desaparecido> update(@PathVariable Integer id, @RequestBody Desaparecido d){
        if (!desaparecidoRepository.existsById(id)) return ResponseEntity.status(404).build();

        d.setId(id);
        desaparecidoRepository.save(d);
        return ResponseEntity.status(200).body(d);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        if(!desaparecidoRepository.existsById(id)) return  ResponseEntity.status(404).build();

        desaparecidoRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
