package com.nexus.backend.controllers;

import com.nexus.backend.dto.associado.AssociadoCriacaoDto;
import com.nexus.backend.dto.associado.AssociadoRespostaDto;
import com.nexus.backend.entities.Associado;
import com.nexus.backend.mappers.AssociadoMapper;
import com.nexus.backend.repositories.AssociadoRepository;
import com.nexus.backend.service.AssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/associados")
@RequiredArgsConstructor
public class AssociadoController {

    private final AssociadoService associadoService;
    private final AssociadoMapper associadoMapper;

    // Cadastrar associado
    @PostMapping
    public ResponseEntity<AssociadoRespostaDto> cadastrarAssociado(@RequestBody @Valid AssociadoCriacaoDto a){
        Associado entity = associadoMapper.toCriacaoEntity(a);
        Associado associadoSalvo = associadoService.register(entity);
        AssociadoRespostaDto returnDto = associadoMapper.toRespostaDto(associadoSalvo);
        return ResponseEntity.created(null).body(returnDto);
    }

    // Listar todos os associados
    @GetMapping
    public ResponseEntity<List<AssociadoRespostaDto>> listarAssociados() {
        List<Associado> associados = associadoService.getAll();
        if(associados.isEmpty()) return ResponseEntity.noContent().build();
        List<AssociadoRespostaDto> associadoRespostaDtoList = associados
                .stream()
                .map(associadoMapper::toRespostaDto)
                .toList();
        return ResponseEntity.ok(associadoRespostaDtoList);
    }

    // Buscar associado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Associado> buscarPorId(@PathVariable int id){
        return ResponseEntity.ok(associadoService.getById(id));
    }

    // Atualizar associado
    @PutMapping("/{id}")
    public ResponseEntity<Associado> atualizarAssociado(@PathVariable int id, @RequestBody @Valid Associado a){
        return ResponseEntity.ok(associadoService.update(id,a));
    }

    // Deletar associado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssociado(@PathVariable int id){
        associadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
