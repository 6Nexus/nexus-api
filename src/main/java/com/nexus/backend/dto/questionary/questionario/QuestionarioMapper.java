package com.nexus.backend.dto.questionary.questionario;

import com.nexus.backend.entities.questionary.Questionario;

public class QuestionarioMapper {
    public static Questionario toEntidadeDto(QuestionarioCriacaoDto dto) {
        if (dto == null) return null;

        return Questionario.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .build();
    }
}
