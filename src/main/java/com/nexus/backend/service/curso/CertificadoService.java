package com.nexus.backend.service.curso;

import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.entities.curso.Matricula;
import com.nexus.backend.service.curso.questionario.ProgressoQuestionarioService;
import com.nexus.backend.service.curso.questionario.QuestionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificadoService {
    private final MatriculaService matriculaService;
    private final ModuloService moduloService;
    private final ProgressoQuestionarioService progressoQuestionarioService;

    public List<Curso> certificadosDisponiveis(Integer idAssociado, List<Curso> cursosDoAssociado) {
        return cursosDoAssociado.stream()
                .filter(curso -> {
                    Integer matriculaDoAssocioado = matriculaService.buscarPorAssociadoECurso(idAssociado, curso.getId());
                    Integer quantidadeQuestionariosPorCurso = moduloService.qtdModulosPorCurso(curso.getId());
                    Integer quantidadeDePontuacoesMaiorQue70 = progressoQuestionarioService.buscarPontuacaoPorMatricula(matriculaDoAssocioado, 70.0);

                    return quantidadeQuestionariosPorCurso.equals(quantidadeDePontuacoesMaiorQue70);
                }).toList();
    }
}
