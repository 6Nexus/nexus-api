package com.nexus.backend.dto.curso.curtida;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CurtidaCriacaoDto {
    @Positive
    @NotNull
    private Integer idAssociado;
    @Positive @NotNull
    private Integer idCurso;
}
