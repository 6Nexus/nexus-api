package com.nexus.backend.service.curso.questionario;

import com.nexus.backend.entities.curso.questionario.Questionario;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.questionario.QuestionarioRepository;
import com.nexus.backend.service.curso.ModuloService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionarioService {
    private final QuestionarioRepository questionarioRepository;
    private final ModuloService moduloService;

    public Questionario cadastrar(Questionario questionario, Integer idModulo) {
        questionario.setModulo(moduloService.buscarPorId(idModulo));
        return questionarioRepository.save(questionario);
    }

    public Questionario buscarPorModulo(Integer idModulo) {
        return questionarioRepository.findByModuloId(idModulo)
                .orElseThrow(() -> new EntityNotFoundException("Questionário"));
    }
}
