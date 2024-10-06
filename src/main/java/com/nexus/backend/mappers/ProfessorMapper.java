package com.nexus.backend.mappers;

import com.nexus.backend.dto.professor.ProfessorCriacaoDto;
import com.nexus.backend.dto.professor.ProfessorRespostaDto;
import com.nexus.backend.entities.Associado;
import com.nexus.backend.entities.Professor;
import com.nexus.backend.enums.TipoUsuario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper extends UsuarioMapper<Professor, ProfessorCriacaoDto> {
    public ProfessorRespostaDto toRespostaDto(Professor entity){
        if (entity==null) return null;

        return ProfessorRespostaDto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .areaAtuacao(entity.getAreaAtuacao())
                .build();
    }

    public Professor toCriacaoEntity (@Valid ProfessorCriacaoDto dto){
        if (dto==null) return null;

        Professor professor = super.toEntity(dto);
        professor.setTipoUsuario(TipoUsuario.PROFESSOR);
        professor.setAreaAtuacao(dto.getAreaAtuacao());

        return professor;
    }
}
