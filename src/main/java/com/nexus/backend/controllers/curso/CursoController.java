package com.nexus.backend.controllers.curso;

import com.nexus.backend.dto.curso.curso.CursoCriacaoDto;
import com.nexus.backend.dto.curso.curso.CursoRespostaDto;
import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.mappers.curso.CursoMapper;
import com.nexus.backend.service.curso.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {
    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody @Valid CursoCriacaoDto cursoCriacaoDto) {
        Curso cursoEntrada = CursoMapper.toEntidade(cursoCriacaoDto);
        Integer idCursoSalvo = cursoService.cadastrar(cursoEntrada, cursoCriacaoDto.getIdProfessor());

        return ResponseEntity.created(null).body(idCursoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<CursoRespostaDto>> listar() {
        List<Curso> cursosEncontrados = cursoService.listar();
        if (cursosEncontrados.isEmpty()) return ResponseEntity.noContent().build();

        List<CursoRespostaDto> cursosMapeados = cursosEncontrados.stream()
                .map(CursoMapper::toRespostaDto)
                .toList();

        return ResponseEntity.ok(cursosMapeados);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<CursoRespostaDto>> listarPorCategoria(@PathVariable String categoria) {
        List<Curso> cursosEncontrados = cursoService.listarPorCategoria(categoria);
        if (cursosEncontrados.isEmpty()) return ResponseEntity.noContent().build();

        List<CursoRespostaDto> cursosMapeados = cursosEncontrados.stream()
                .map(CursoMapper::toRespostaDto)
                .toList();

        return ResponseEntity.ok(cursosMapeados);
    }

    @GetMapping("/professor/{idProfessor}")
    public ResponseEntity<List<CursoRespostaDto>> listarPorProfessor(@PathVariable Integer idProfessor) {
        List<Curso> cursosEncontrados = cursoService.listarPorProfessor(idProfessor);
        if (cursosEncontrados.isEmpty()) return ResponseEntity.noContent().build();

        List<CursoRespostaDto> cursosMapeados = cursosEncontrados.stream()
                .map(CursoMapper::toRespostaDto)
                .toList();

        return ResponseEntity.ok(cursosMapeados);
    }

    @PatchMapping("/titulo/{idCurso}/{titulo}")
    public ResponseEntity<String> alterarTitulo(
            @PathVariable Integer idCurso,
            @PathVariable String titulo) {
        String tituloAlterado = cursoService.alterarTitulo(idCurso, titulo);

        return ResponseEntity.ok(tituloAlterado);
    }

    @PatchMapping("/categoria/{idCurso}/{categoria}")
    public ResponseEntity<String> alterarCategoria(
            @PathVariable Integer idCurso,
            @PathVariable String categoria) {
        String categoriaAlterada = cursoService.alterarCategoria(idCurso, categoria);

        return ResponseEntity.ok(categoriaAlterada);
    }

    @PatchMapping("/descricao/{idCurso}/{descricao}")
    public ResponseEntity<String> alterarDescricao(
            @PathVariable Integer idCurso,
            @PathVariable String descricao) {
        String descricaoAlterada = cursoService.alterarDescricao(idCurso, descricao);

        return ResponseEntity.ok(descricaoAlterada);
    }

    @DeleteMapping("/{idCurso}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idCurso) {
        cursoService.deletar(idCurso);
        return ResponseEntity.noContent().build();
    }
}
