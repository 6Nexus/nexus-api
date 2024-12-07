package com.nexus.backend.dto.curso.video;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VideoCriacaoDto {
    @NotBlank
    private String titulo;
    @NotBlank
    private String descricao;
    @Positive @NotNull
    private Integer ordem;
    @Positive @NotNull
    private Integer idModulo;
}
