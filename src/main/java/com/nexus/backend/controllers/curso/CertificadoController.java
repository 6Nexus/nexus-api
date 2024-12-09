package com.nexus.backend.controllers.curso;

import com.nexus.backend.dto.curso.certificado.CertificadoRespostaDto;
import com.nexus.backend.dto.curso.curso.CursoRespostaDto;
import com.nexus.backend.entities.curso.Certificado;
import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.mappers.curso.CertificadoMapper;
import com.nexus.backend.mappers.curso.CursoMapper;
import com.nexus.backend.service.curso.CertificadoService;
import com.nexus.backend.service.curso.CursoService;
import com.nexus.backend.service.curso.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certificados")
@RequiredArgsConstructor
public class CertificadoController {
    private final CertificadoService certificadoService;
    private final CursoService cursoService;
    private final MatriculaService matriculaService;

    @GetMapping("{associadoId}")
    public ResponseEntity<List<CertificadoRespostaDto>> listarCertificados(@PathVariable Integer associadoId) {
        List<Integer> idsCursosMatriculados = matriculaService.idCursosMatriculados(associadoId);
        List<Curso> cursosEncontrados = cursoService.listarPorIds(idsCursosMatriculados);
        if (cursosEncontrados.isEmpty()) return ResponseEntity.noContent().build();

        List<Certificado> certificadosDisponiveis = certificadoService.certificadosDisponiveis(associadoId, cursosEncontrados);
        if (certificadosDisponiveis.isEmpty()) return ResponseEntity.noContent().build();

        List<CertificadoRespostaDto> certificadosMapeados = certificadosDisponiveis.stream()
                .map(CertificadoMapper::toRespostaDto)
                .toList();

        return ResponseEntity.ok(certificadosMapeados);
    }
}
