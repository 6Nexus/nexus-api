package com.nexus.backend.mappers.curso.questionario.pergunta;

import com.nexus.backend.dto.curso.questionario.questionario.QuestionarioCriacaoDto;
import com.nexus.backend.entities.curso.questionario.Pergunta;

public class PerguntaMapper {
    public static Pergunta toEntidadeDto(QuestionarioCriacaoDto.PerguntaDto perguntaDtoCriacao) {
        if (perguntaDtoCriacao == null) return null;

        return Pergunta.builder()
                        .pergunta(perguntaDtoCriacao.getPergunta())
                        .build();
    }
}
