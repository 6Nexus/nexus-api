package com.nexus.backend.service.curso;

import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.entities.curso.Matricula;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.MatriculaRepository;
import com.nexus.backend.service.AssociadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final AssociadoService associadoService;
    private final CursoService cursoService;

    public Integer cadastrar(Integer associadoId, Integer cursoId) {
        Matricula matriculaACadastrar = Matricula.builder()
                .curso(cursoService.buscarPorId(cursoId))
                .associado(associadoService.getById(associadoId))
                .certificadoEmitido(false)
                .build();
        return matriculaRepository.save(matriculaACadastrar).getId();
    }

    public List<Integer> idCursosMatriculados(Integer idAssociado) {
        return matriculaRepository.findAllByAssociadoId(idAssociado).stream()
                .map(matricula -> {
                    return matricula.getCurso().getId();
                })
                .collect(Collectors.toList());
    }

    public Integer buscarPorAssociadoECurso(Integer idAssociado, Integer idCurso) {
        Optional<Matricula> matriculaEncontrada = matriculaRepository.findByAssociadoIdAndCursoId(idAssociado, idCurso);

        if (matriculaEncontrada.isEmpty()) throw new EntityNotFoundException("Matrícula");

        return matriculaEncontrada.get().getId();
    }

    public Matricula buscarPorId(Integer idMatricula) {
        return matriculaRepository.findById(idMatricula).orElseThrow(
                () -> new EntityNotFoundException("Matrícula")
        );
    }
}
