package com.nexus.backend.controllers.curso;

import com.nexus.backend.dto.curso.curso.CursoRespostaDto;
import com.nexus.backend.dto.curso.curtida.CurtidaCriacaoDto;
import com.nexus.backend.entities.curso.Curtida;
import com.nexus.backend.mappers.curso.CursoMapper;
import com.nexus.backend.service.curso.CurtidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curtidas")
@RequiredArgsConstructor
public class CurtidaController {

    private final CurtidaService curtidaService;

    @PostMapping
    public ResponseEntity<Void> curtirCurso(@RequestBody @Valid CurtidaCriacaoDto curtidaCriacaoDto) {
        curtidaService.curtirCurso(curtidaCriacaoDto.getIdAssociado(), curtidaCriacaoDto.getIdCurso());
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/{idAssociado}")
    public ResponseEntity<List<CursoRespostaDto>> buscarCurtidosPorAssociado(@PathVariable Integer idAssociado) {
        List<Curtida> cursosCurtidos = curtidaService.buscarCurtidosPorAssociado(idAssociado);
        if (cursosCurtidos.isEmpty()) return ResponseEntity.noContent().build();

        List<CursoRespostaDto> cursosCurtidosMapeados = cursosCurtidos.stream().
                map(CursoMapper::toRespostaDto).
                toList();

        return ResponseEntity.ok().body(cursosCurtidosMapeados);
    }

    @DeleteMapping("/{idAssociado}/{idCurso}")
    public ResponseEntity<Void> deletarCurtida(@PathVariable Integer idAssociado, @PathVariable Integer idCurso) {
        curtidaService.deletarCurtida(idAssociado, idCurso);
        return ResponseEntity.noContent().build();
    }
}
