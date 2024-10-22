package com.nexus.backend.service.questionary;

import com.nexus.backend.entities.questionary.Pergunta;
import com.nexus.backend.entities.questionary.Questionario;
import com.nexus.backend.entities.questionary.Resposta;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.questionary.PerguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerguntaService {
    private final PerguntaRepository perguntaRepository;

    public Pergunta criar(Pergunta pergunta, Questionario questionario) {
        pergunta.setQuestionario(questionario);
        return perguntaRepository.save(pergunta);
    }

    public Pergunta adicionarRespostaCorreta(Pergunta pergunta, Resposta resposta) {
        pergunta.setRespostaCerta(resposta);
        return perguntaRepository.save(pergunta);
    }

    public Pergunta buscarPorId(Integer questionarioId) {
        return perguntaRepository.findByQuestionarioId(questionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Pergunta"));
    }
}
