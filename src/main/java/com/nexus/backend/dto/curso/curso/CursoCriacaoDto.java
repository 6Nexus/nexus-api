package com.nexus.backend.dto.curso.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CursoCriacaoDto {
    @NotBlank
    private String titulo;
    @NotBlank
    private String categoria;
    @NotBlank
    private String descricao;
    @Positive @NotNull
    private Integer idProfessor;
}
