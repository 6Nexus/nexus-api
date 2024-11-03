package com.nexus.backend.dto.curso.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRespostaDto {
    private Integer id;
    private Integer ordem;
    private String titulo;
    private String descricao;
    private Boolean carregadoNoYoutube;
    private String youtubeUrl;
}
