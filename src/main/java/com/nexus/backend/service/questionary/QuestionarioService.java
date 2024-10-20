package com.nexus.backend.service.questionary;

import com.nexus.backend.entities.questionary.Questionario;
import com.nexus.backend.repositories.questionary.QuestionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionarioService {
    private final QuestionarioRepository questionarioRepository;

    public Questionario criar(Questionario questionarioEntidade) {
        return questionarioRepository.save(questionarioEntidade);
    }
}
