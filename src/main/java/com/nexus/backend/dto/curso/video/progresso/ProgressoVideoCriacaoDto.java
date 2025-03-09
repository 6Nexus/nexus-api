package com.nexus.backend.dto.curso.video.progresso;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProgressoVideoCriacaoDto {
    @Positive @NotNull
    private Integer matriculaId;
    @Positive @NotNull
    private Integer videoId;
}
