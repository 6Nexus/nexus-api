package com.nexus.backend.mappers.questionario.pergunta;

import com.nexus.backend.dto.questionario.questionario.QuestionarioCriacaoDto;
import com.nexus.backend.entities.questionario.Pergunta;

public class PerguntaMapper {
    public static Pergunta toEntidadeDto(QuestionarioCriacaoDto.PerguntaDto perguntaDtoCriacao) {
        if (perguntaDtoCriacao == null) return null;

        return Pergunta.builder()
                        .pergunta(perguntaDtoCriacao.getPergunta())
                        .build();
    }
}
