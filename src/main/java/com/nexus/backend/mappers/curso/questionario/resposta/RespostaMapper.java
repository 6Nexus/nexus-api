package com.nexus.backend.mappers.curso.questionario.resposta;

import com.nexus.backend.dto.curso.questionario.questionario.QuestionarioCriacaoDto;
import com.nexus.backend.entities.curso.questionario.Resposta;

public class RespostaMapper {
    public static Resposta toEntidadeDto(QuestionarioCriacaoDto.PerguntaDto.RespostaDto respostaCriacaoDto) {
        if (respostaCriacaoDto == null) return null;

        return Resposta.builder()
                .resposta(respostaCriacaoDto.getResposta())
                .build();
    }
}
