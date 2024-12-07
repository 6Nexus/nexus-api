package com.nexus.backend.mappers.curso.video;

import com.nexus.backend.dto.curso.video.VideoCriacaoDto;
import com.nexus.backend.dto.curso.video.VideoRespostaDto;
import com.nexus.backend.entities.curso.video.Video;

import java.time.LocalDateTime;

public class VideoMapper {
    public static Video toEntidade(VideoCriacaoDto dto) {
        if (dto == null) return null;

        return Video.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .ordem(dto.getOrdem())
                .carregadoNoYoutube(false)
                .build();
    }

    public static VideoRespostaDto toRespostaDto(Video video) {
        if (video == null) return null;

        return VideoRespostaDto.builder()
                .id(video.getId())
                .ordem(video.getOrdem())
                .titulo(video.getTitulo())
                .descricao(video.getDescricao())
                .carregadoNoYoutube(video.getCarregadoNoYoutube())
                .youtubeUrl(video.getYoutubeUrl())
                .build();
    }
}
