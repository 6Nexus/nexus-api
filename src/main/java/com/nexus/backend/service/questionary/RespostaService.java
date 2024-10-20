package com.nexus.backend.service.questionary;

import com.nexus.backend.entities.questionary.Pergunta;
import com.nexus.backend.entities.questionary.Resposta;
import com.nexus.backend.repositories.questionary.RespostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
