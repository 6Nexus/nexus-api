package com.nexus.backend.dto.questionary.resposta;

import com.nexus.backend.dto.questionary.questionario.QuestionarioCriacaoDto;
import com.nexus.backend.entities.questionary.Resposta;

public class RespostaMapper {
    public static Resposta toEntidadeDto(QuestionarioCriacaoDto.PerguntaDto.RespostaDto respostaCriacaoDto) {
        if (respostaCriacaoDto == null) return null;

        return Resposta.builder()
                .resposta(respostaCriacaoDto.getResposta())
                .build();
    }
}
