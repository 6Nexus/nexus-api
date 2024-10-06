package com.nexus.backend.controllers;

import com.nexus.backend.dto.desaparecido.DesaparecidoCriacaoDto;
import com.nexus.backend.dto.desaparecido.DesaparecidoRespostaDto;
import com.nexus.backend.entities.Desaparecido;
import com.nexus.backend.mappers.DesaparecidoMapper;
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
    private final DesaparecidoMapper desaparecidoMapper;

    @GetMapping
    public ResponseEntity<List<DesaparecidoRespostaDto>> listarDesaparecidos(){
        List<Desaparecido> desaparecidos = desaparecidoService.getAll();

        if (desaparecidos.isEmpty()) return ResponseEntity.noContent().build();

        List<DesaparecidoRespostaDto> desaparecidoRespostaDtoList = desaparecidos
                .stream()
                .map(desaparecidoMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(desaparecidoRespostaDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DesaparecidoRespostaDto> getById(@PathVariable Integer id){
        Desaparecido d = desaparecidoService.getById(id);
        DesaparecidoRespostaDto dto =desaparecidoMapper.toRespostaDto(d);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/dataOcorrencia")
    public ResponseEntity<List<DesaparecidoRespostaDto>> getDataOcorrencia(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        List<Desaparecido> desaparecidos = desaparecidoService.getByDataOcorrencia(inicio,fim);

        if (desaparecidos.isEmpty()) return ResponseEntity.noContent().build();

        List<DesaparecidoRespostaDto> desaparecidoRespostaDtoList = desaparecidos
                .stream()
                .map(desaparecidoMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(desaparecidoRespostaDtoList);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<DesaparecidoRespostaDto>> getByNome(@RequestParam String nome){
        List<Desaparecido> desaparecidos = desaparecidoService.getByNome(nome);
        if (desaparecidos.isEmpty()) return ResponseEntity.noContent().build();

        List<DesaparecidoRespostaDto> desaparecidoRespostaDtoList = desaparecidos
                .stream()
                .map(desaparecidoMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(desaparecidoRespostaDtoList);
    }

    @PostMapping
    public ResponseEntity<DesaparecidoRespostaDto> register(@RequestBody @Valid DesaparecidoCriacaoDto d){
        Desaparecido entity = desaparecidoMapper.toCriacaoEntity(d);
        Desaparecido dSalvo = desaparecidoService.register(entity);
        return ResponseEntity.created(null).body(desaparecidoMapper.toRespostaDto(dSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DesaparecidoRespostaDto> update(@PathVariable Integer id, @RequestBody @Valid DesaparecidoCriacaoDto d){
        Desaparecido entity = desaparecidoMapper.toCriacaoEntity(d);
        Desaparecido dSalvo = desaparecidoService.update(id,entity);
        return ResponseEntity.created(null).body(desaparecidoMapper.toRespostaDto(dSalvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        desaparecidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
