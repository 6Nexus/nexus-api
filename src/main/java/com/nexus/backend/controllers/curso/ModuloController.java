package com.nexus.backend.controllers.curso;

import com.nexus.backend.dto.curso.modulo.ModuloCriacaoDto;
import com.nexus.backend.dto.curso.modulo.ModuloRespostaDto;
import com.nexus.backend.entities.curso.Modulo;
import com.nexus.backend.mappers.curso.ModuloMapper;
import com.nexus.backend.service.curso.ModuloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modulos")
@RequiredArgsConstructor
public class ModuloController {
    private final ModuloService moduloService;

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody @Valid ModuloCriacaoDto moduloCriacaoDto) {
        Modulo moduloEntrada = ModuloMapper.toEntidade(moduloCriacaoDto);
        Integer idModuloSalvo = moduloService.cadastrar(moduloEntrada, moduloCriacaoDto.getIdCurso());

        return ResponseEntity.created(null).body(idModuloSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuloRespostaDto> listarPorId(@PathVariable Integer id) {
        Modulo moduloEncontrado = moduloService.buscarPorId(id);
        ModuloRespostaDto moduloEncontradoDto = ModuloMapper.toRespostaDto(moduloEncontrado);
        return ResponseEntity.ok(moduloEncontradoDto);
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<ModuloRespostaDto>> listarPorCurso(@PathVariable Integer idCurso) {
        List<Modulo> modulosEncontrados = moduloService.listarPorCurso(idCurso);
        if (modulosEncontrados.isEmpty()) return ResponseEntity.noContent().build();

        List<ModuloRespostaDto> modulosMapeados = modulosEncontrados.stream()
                .map(ModuloMapper::toRespostaDto)
                .toList();

        return ResponseEntity.ok(modulosMapeados);
    }

    @PatchMapping("/ordem/{idModulo01}/{idModulo02}")
    public ResponseEntity<Void> alterarTitulo(
            @PathVariable Integer idModulo01,
            @PathVariable Integer idModulo02) {
        moduloService.alterarOrdem(idModulo01, idModulo02);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{idModulo}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idModulo) {
        moduloService.deletar(idModulo);
        return ResponseEntity.noContent().build();
    }

}
