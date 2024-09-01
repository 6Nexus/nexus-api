package com.nexus.backend.controllers;

import com.nexus.backend.entities.Desaparecido;
import com.nexus.backend.repositories.DesaparecidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Desaparecido> register(@RequestBody Desaparecido d){
        d.setId(null);
        Desaparecido novoResgistro =desaparecidoRepository.save(d);
        return ResponseEntity.status(201).body(novoResgistro);
    }

}
