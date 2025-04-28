package com.nexus.backend.service.curso;

import com.nexus.backend.entities.curso.Curtida;
import com.nexus.backend.exceptions.BadRequestException;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.CurtidaRepository;
import com.nexus.backend.service.AssociadoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurtidaService {
    private final CurtidaRepository curtidaRepository;
    private final CursoService cursoService;
    private final AssociadoService associadoService;

    public void curtirCurso(Integer idAssociado, Integer idCurso) {
            Optional<Curtida> curtidaEncontrada = curtidaRepository.findByAssociadoIdAndCursoId(idAssociado, idCurso);

            if (curtidaEncontrada.isPresent()) {
                throw new BadRequestException("Curtida");
            }
            Curtida novaCurtida = Curtida.builder()
                    .curso(cursoService.buscarPorId(idCurso))
                    .associado(associadoService.getById(idAssociado))
                    .build();
            curtidaRepository.save(novaCurtida);
    }

    public List<Curtida> buscarCurtidosPorAssociado(Integer idAssociado) {
        List<Curtida> curtidas =  curtidaRepository.findByAssociadoId(idAssociado);
        if (curtidas.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return curtidas;
    }

    public Boolean existeCurtidaPorAssociadoECurso(Integer idAssociado, Integer idCurso) {
        return curtidaRepository.existsByAssociadoIdAndCursoId(idAssociado, idCurso);
    }

    @Transactional
    public void deletarCurtida(Integer idAssociado, Integer idCurso) {
        if (!curtidaRepository.existsByAssociadoIdAndCursoId(idAssociado, idCurso)) throw new EntityNotFoundException("Curtida");
        curtidaRepository.deleteByAssociadoIdAndCursoId(idAssociado, idCurso);
    }
}
