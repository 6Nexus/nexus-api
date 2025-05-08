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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificados")
@RequiredArgsConstructor
public class CertificadoController {
    private final CertificadoService certificadoService;

    @PostMapping()
    public ResponseEntity<Void> emitirCertificados() {
        certificadoService.enviarCertificados();

        return ResponseEntity.ok().build();
    }
}
