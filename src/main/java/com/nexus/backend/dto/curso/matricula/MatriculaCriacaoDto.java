package com.nexus.backend.dto.curso.matricula;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MatriculaCriacaoDto {
    @Positive @NotNull
    private Integer idAssociado;
    @Positive @NotNull
    private Integer idCurso;
}
