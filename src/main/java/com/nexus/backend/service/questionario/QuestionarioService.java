package com.nexus.backend.service.questionario;

import com.nexus.backend.entities.questionario.Questionario;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.questionario.QuestionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionarioService {
    private final QuestionarioRepository questionarioRepository;

    public Questionario criar(Questionario questionarioEntidade) {
        return questionarioRepository.save(questionarioEntidade);
    }

    public Questionario buscarPorId(Integer id) {
        return questionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Questionário"));
    }
}
