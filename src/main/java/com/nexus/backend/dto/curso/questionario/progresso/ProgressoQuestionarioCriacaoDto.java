package com.nexus.backend.dto.curso.questionario.progresso;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProgressoQuestionarioCriacaoDto {
    @NotNull
    private Double pontuacao;
    @Positive @NotNull
    private Integer matriculaId;
    @Positive @NotNull
    private Integer questionarioId;
}
