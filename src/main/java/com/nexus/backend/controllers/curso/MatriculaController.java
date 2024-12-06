package com.nexus.backend.controllers.curso;

import com.nexus.backend.dto.curso.matricula.MatriculaCriacaoDto;
import com.nexus.backend.service.curso.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController {
    private final MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody @Valid MatriculaCriacaoDto matriculaCriacaoDto) {
        Integer idMatriculaSalva = matriculaService.cadastrar(matriculaCriacaoDto.getIdAssociado(), matriculaCriacaoDto.getIdCurso());

        return ResponseEntity.created(null).body(idMatriculaSalva);
    }

    @GetMapping("/{idAssociado}/{idCurso}")
    public ResponseEntity<Integer> listarPorId(@PathVariable Integer idAssociado, @PathVariable Integer idCurso) {
        Integer matriculaEncontrada = matriculaService.buscarPorAssociadoECurso(idAssociado, idCurso);

        return ResponseEntity.ok(matriculaEncontrada);
    }
}

