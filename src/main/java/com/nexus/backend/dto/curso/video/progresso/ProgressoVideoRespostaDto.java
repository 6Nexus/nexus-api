package com.nexus.backend.dto.curso.video.progresso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressoVideoRespostaDto {
    private Integer id;
    private Boolean assistido;
    private Integer matriculaId;
    private Integer videoId;
}
