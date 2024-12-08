package com.nexus.backend.mappers.curso;

import com.nexus.backend.dto.curso.curso.CursoCriacaoDto;
import com.nexus.backend.dto.curso.curso.CursoRespostaDto;
import com.nexus.backend.dto.curso.modulo.ModuloCriacaoDto;
import com.nexus.backend.dto.curso.modulo.ModuloRespostaDto;
import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.entities.curso.Modulo;

import java.time.LocalDateTime;

public class ModuloMapper {
    public static Modulo toEntidade(ModuloCriacaoDto dto) {
        if (dto == null) return null;

        return Modulo.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .ordem(dto.getOrdem())
                .build();
    }

    public static ModuloRespostaDto toRespostaDto(Modulo modulo) {
        if (modulo == null) return null;

        return ModuloRespostaDto.builder()
                .id(modulo.getId())
                .titulo(modulo.getTitulo())
                .descricao(modulo.getDescricao())
                .ordem(modulo.getOrdem())
                .criadoEm(modulo.getCriadoEm())
                .build();
    }
}
