package com.nexus.backend.service.curso.questionario;

import com.nexus.backend.entities.curso.questionario.Pergunta;
import com.nexus.backend.entities.curso.questionario.Resposta;
import com.nexus.backend.repositories.curso.questionario.RespostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RespostaService {
    private final PerguntaService perguntaService;
    private final RespostaRepository respostaRepository;

    public Resposta criar(Resposta resposta, Boolean respostaCorreta, Pergunta pergunta) {
        resposta.setPergunta(pergunta);
        Resposta respostaSalvaNoBanco = respostaRepository.save(resposta);

        if (respostaCorreta) perguntaService.adicionarRespostaCorreta(pergunta, respostaSalvaNoBanco);
        return respostaSalvaNoBanco;
    }

    public List<Resposta> buscarPorIdPerguntas(List<Integer> ids) {
        return respostaRepository.buscarPorIds(ids);
    }
}
