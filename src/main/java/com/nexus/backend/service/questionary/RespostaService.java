package com.nexus.backend.service.questionary;

import com.nexus.backend.entities.questionary.Pergunta;
import com.nexus.backend.entities.questionary.Resposta;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.questionary.RespostaRepository;
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

    public List<Resposta> buscarPorId(Integer perguntaId) {
        return respostaRepository.findAllByPerguntaId(perguntaId);
    }
}
