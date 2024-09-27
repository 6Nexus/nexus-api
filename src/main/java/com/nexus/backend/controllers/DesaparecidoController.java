package com.nexus.backend.controllers;

import com.nexus.backend.entities.Desaparecido;
import com.nexus.backend.repositories.DesaparecidoRepository;
import com.nexus.backend.service.DesaparecidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/desaparecidos")
@RequiredArgsConstructor
public class DesaparecidoController {

    private final DesaparecidoService desaparecidoService;

    @GetMapping
    public ResponseEntity<List<Desaparecido>> listarDesaparecidos(){
        List<Desaparecido> desaparecidos = desaparecidoService.getAll();

        if (desaparecidos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(desaparecidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Desaparecido> getById(@PathVariable Integer id){
        return ResponseEntity.ok(desaparecidoService.getById(id));
    }

    @GetMapping("/dataOcorrencia")
    public ResponseEntity<List<Desaparecido>> getDataOcorrencia(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        List<Desaparecido> desaparecidos = desaparecidoService.getByDataOcorrencia(inicio,fim);

        if (desaparecidos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(desaparecidos);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Desaparecido>> getByNome(@RequestParam String nome){
        List<Desaparecido> desaparecidos = desaparecidoService.getByNome(nome);

        if (desaparecidos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(desaparecidos);
    }

    @PostMapping
    public ResponseEntity<Desaparecido> register(@RequestBody @Valid Desaparecido d){
        return ResponseEntity.created(null).body(desaparecidoService.register(d));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Desaparecido> update(@PathVariable Integer id, @RequestBody @Valid Desaparecido d){
        return ResponseEntity.ok(desaparecidoService.update(id, d));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        desaparecidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
