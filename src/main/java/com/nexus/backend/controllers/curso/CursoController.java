package com.nexus.backend.controllers.curso;

import com.nexus.backend.dto.curso.curso.CursoAssociadoRespostaDto;
import com.nexus.backend.dto.curso.curso.CursoCriacaoDto;
import com.nexus.backend.dto.curso.curso.CursoRespostaDto;
import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.mappers.curso.CursoMapper;
import com.nexus.backend.service.curso.CursoService;
import com.nexus.backend.service.curso.CurtidaService;
import com.nexus.backend.service.curso.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {
    private final CursoService cursoService;
    private final MatriculaService matriculaService;
    private final CurtidaService curtidaService;

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody @Valid CursoCriacaoDto cursoCriacaoDto) {
        Curso cursoEntrada = CursoMapper.toEntidade(cursoCriacaoDto);
        Integer idCursoSalvo = cursoService.cadastrar(cursoEntrada, cursoCriacaoDto.getIdProfessor());

        return ResponseEntity.created(null).body(idCursoSalvo);
    }

    @PatchMapping(value = "/capa/{cursoId}", consumes = "image/*")
    public ResponseEntity<Void> patchCapa(@PathVariable Integer cursoId, @RequestBody byte[] capa) {
        cursoService.cadastrarCapa(cursoId, capa);
        return ResponseEntity.status(200).build();
    }

    @GetMapping(value = "/capa/{cursoId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getCapa(@PathVariable Integer cursoId) {
        byte[] capa = cursoService.buscarCapaPorCursoId(cursoId);
        return ResponseEntity.status(200).body(capa);
    }

    @GetMapping
    public ResponseEntity<List<CursoAssociadoRespostaDto>> listar(@RequestParam Integer idAssociado) {
        List<Curso> cursosEncontrados = cursoService.listar();
        if (cursosEncontrados.isEmpty()) return ResponseEntity.noContent().build();

        List<CursoAssociadoRespostaDto> cursosMapeados = cursosEncontrados.stream()
                .map(c -> CursoMapper.toRespostaAssociadoDto(c, curtidaService.existeCurtidaPorAssociadoECurso(idAssociado, c.getId())))
                .toList();

        return ResponseEntity.ok(cursosMapeados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoRespostaDto> listarPorId(@PathVariable Integer id) {
        Curso cursoEncontrado = cursoService.buscarPorId(id);
        CursoRespostaDto cursoEncontradoDto = CursoMapper.toRespostaDto(cursoEncontrado);
        return ResponseEntity.ok(cursoEncontradoDto);
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

    @GetMapping("/associado/{idAssociado}/categoria/{categoria}")
    public ResponseEntity<List<CursoAssociadoRespostaDto>> listarPorCategoria(@PathVariable String categoria, @PathVariable Integer idAssociado ) {
        List<Curso> cursosEncontrados = cursoService.listarPorCategoria(categoria);
        if (cursosEncontrados.isEmpty()) return ResponseEntity.noContent().build();

        List<CursoAssociadoRespostaDto> cursosMapeados = cursosEncontrados.stream()
                .map(c -> CursoMapper.toRespostaAssociadoDto(c, curtidaService.existeCurtidaPorAssociadoECurso(idAssociado, c.getId())))
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

    private List<Curso> cursosDoAssociado(Integer idAssociado) {
        List<Integer> idsCursosMatriculados = matriculaService.idCursosMatriculados(idAssociado);
        List<Curso> cursosEncontrados = cursoService.listarPorIds(idsCursosMatriculados);

        return cursosEncontrados;
    }

    @GetMapping("/associado/{idAssociado}")
    public ResponseEntity<List<CursoAssociadoRespostaDto>> listarPorAssociado(@PathVariable Integer idAssociado) {
        List<Curso> cursosDoAssociadoEncontrados = cursosDoAssociado(idAssociado);
        if (cursosDoAssociadoEncontrados.isEmpty()) return ResponseEntity.noContent().build();

        List<CursoAssociadoRespostaDto> cursosMapeados = cursosDoAssociadoEncontrados.stream()
                .map(c -> CursoMapper.toRespostaAssociadoDto(c, curtidaService.existeCurtidaPorAssociadoECurso(idAssociado, c.getId())))
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

    @PutMapping("/{id}")
    public ResponseEntity<CursoRespostaDto> attCurso(@PathVariable int id,@RequestBody @Valid CursoCriacaoDto c){
        Curso entity = CursoMapper.toEntidade(c);
        int idProf = c.getIdProfessor();
        Curso cursoSalvo = cursoService.atualizar(id,idProf, entity);
        return ResponseEntity.created(null).body(CursoMapper.toRespostaDto(cursoSalvo));
    }

    @DeleteMapping("/{idCurso}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idCurso) {
        cursoService.deletar(idCurso);
        return ResponseEntity.noContent().build();
    }
}
