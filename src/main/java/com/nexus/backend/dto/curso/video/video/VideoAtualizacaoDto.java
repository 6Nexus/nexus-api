package com.nexus.backend.dto.curso.video.video;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VideoAtualizacaoDto {
    private String titulo;
    private String descricao;
}
