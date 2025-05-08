package com.nexus.backend.mappers.curso;

import com.nexus.backend.dto.curso.capa.CapaRespostaDto;
import com.nexus.backend.entities.curso.Curso;

public class CapaMapper {
    public static CapaRespostaDto toRespostaDto(Curso curso) {
        if (curso == null) return null;

        return CapaRespostaDto.builder()
                .capaUrl(curso.getCapaUrl())
                .build();
    }
}
