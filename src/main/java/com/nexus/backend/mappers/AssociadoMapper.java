package com.nexus.backend.mappers;

import com.nexus.backend.dto.associado.AssociadoCriacaoDto;
import com.nexus.backend.dto.associado.AssociadoRespostaDto;
import com.nexus.backend.entities.Associado;
import org.springframework.stereotype.Component;

@Component
public class AssociadoMapper extends UsuarioMapper<Associado, AssociadoCriacaoDto>{

    public AssociadoRespostaDto toRespostaDto(Associado entity){
        if (entity==null) return null;

        return AssociadoRespostaDto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .endereco(entity.getEndereco())
                .telefone(entity.getTelefone())
                .grauParentescoComDesaparecido(entity.getGrauParentescoComDesaparecido())
                .build();
    }

    public Associado toCriacaoEntity(AssociadoCriacaoDto dto){
        if (dto==null) return null;

        Associado associado = super.toEntity(dto);

        associado.setGrauParentescoComDesaparecido(dto.getGrauParentescoComDesaparecido());
        associado.setEndereco(dto.getEndereco());

        return associado;
    }
}
