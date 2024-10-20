package com.nexus.backend.dto.questionary.pergunta;

import com.nexus.backend.dto.questionary.questionario.QuestionarioCriacaoDto;
import com.nexus.backend.entities.questionary.Pergunta;

import java.util.stream.Collectors;

public class PerguntaMapper {
    public static Pergunta toEntidadeDto(QuestionarioCriacaoDto.PerguntaDto perguntaDtoCriacao) {
        if (perguntaDtoCriacao == null) return null;

        return Pergunta.builder()
                        .pergunta(perguntaDtoCriacao.getPergunta())
                        .build();
    }
}
