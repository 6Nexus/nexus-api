package com.nexus.backend.mappers.curso.video;

import com.nexus.backend.dto.curso.video.progresso.ProgressoVideoRespostaDto;
import com.nexus.backend.entities.curso.video.ProgressoVideo;

public class ProgressoVideoMapper {
    public static ProgressoVideo toEntidade() {
        return ProgressoVideo.builder()
                .assistido(true)
                .build();
    }

    public static ProgressoVideoRespostaDto toResposta(ProgressoVideo progresso) {
        return ProgressoVideoRespostaDto.builder()
                .id(progresso.getId())
                .assistido(progresso.getAssistido())
                .matriculaId(progresso.getMatricula().getId())
                .videoId(progresso.getVideo().getId())
                .build();
    }
}
