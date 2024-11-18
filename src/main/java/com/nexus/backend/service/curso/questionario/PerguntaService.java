package com.nexus.backend.service.curso.questionario;

import com.nexus.backend.entities.curso.questionario.Pergunta;
import com.nexus.backend.entities.curso.questionario.Questionario;
import com.nexus.backend.entities.curso.questionario.Resposta;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.questionario.PerguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Pergunta> buscarPorIdQuestionario(Integer idQuestionario) {
        return perguntaRepository.findAllByQuestionarioId(idQuestionario);
    }

    public List<Integer> retornarIds(List<Pergunta> perguntas) {
        List<Integer> ids = new ArrayList<>();
        perguntas.forEach(pergunta -> {
            ids.add(pergunta.getId());
        });
        return ids;
    }
}
