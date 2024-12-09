package com.nexus.backend.service.curso;

import com.nexus.backend.entities.curso.Certificado;
import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.entities.curso.questionario.ProgressoQuestionario;
import com.nexus.backend.service.curso.questionario.ProgressoQuestionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificadoService {
    private final MatriculaService matriculaService;
    private final ModuloService moduloService;
    private final ProgressoQuestionarioService progressoQuestionarioService;

    public List<Certificado> certificadosDisponiveis(Integer idAssociado, List<Curso> cursosDoAssociado) {
        List<Integer> idsARemover = new ArrayList<>();
        List<Certificado> certificados = cursosDoAssociado.stream()
                .map(curso -> {
                    Integer matricula = matriculaService.buscarPorAssociadoECurso(idAssociado, curso.getId());
                    Integer quantidadeQuestionariosPorCurso = moduloService.qtdModulosPorCurso(curso.getId());

                    List<ProgressoQuestionario> progressosDoAssociadoNoCurso = progressoQuestionarioService.buscarPorMatricula(matricula).stream()
                            .filter(progressoQuestionario -> progressoQuestionario.getPontuacao() > 70.0)
                            .collect(Collectors.toList());

                    if (progressosDoAssociadoNoCurso.size() != quantidadeQuestionariosPorCurso) {
                        idsARemover.add(curso.getId());
                    }

                    return mapear(curso, dataConclusaoDoCurso(progressosDoAssociadoNoCurso));
                }).toList();

        return certificados.stream()
                .filter(certificado -> !idsARemover.contains(certificado.getCurso().getId()))
                .collect(Collectors.toList());
    }

    private LocalDateTime dataConclusaoDoCurso(List<ProgressoQuestionario> progressos) {
        ProgressoQuestionario ultimoProgresso = progressos.stream()
            .max(Comparator.comparing(ProgressoQuestionario::getDataAtualizacao))
            .orElse(null);

        if (ultimoProgresso == null) {
            throw new ResponseStatusException(204, "Nenhum certificado encontrado", null);
        }

        return ultimoProgresso.getDataAtualizacao();
    }

    private Certificado mapear(Curso curso, LocalDateTime dataConclusao) {
        return new Certificado(dataConclusao, curso);
    }
}
