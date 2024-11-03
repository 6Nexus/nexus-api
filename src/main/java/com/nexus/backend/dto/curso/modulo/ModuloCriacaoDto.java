package com.nexus.backend.dto.curso.modulo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ModuloCriacaoDto {
    @NotBlank
    private String titulo;
    @NotNull
    private Integer ordem;
    @Positive @NotNull
    private Integer idCurso;
}
