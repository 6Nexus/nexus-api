package com.nexus.backend.mappers;

import com.nexus.backend.dto.associado.AssociadoCriacaoDto;
import com.nexus.backend.dto.usuario.UsuarioDto;
import com.nexus.backend.entities.Associado;
import com.nexus.backend.entities.Professor;
import com.nexus.backend.entities.Usuario;

public abstract class UsuarioMapper <entityT extends Usuario, dtoT extends UsuarioDto>  {
    public entityT toEntity(dtoT dto){
        if (dto == null) return null;

        Usuario entity;

        if (dto instanceof AssociadoCriacaoDto){
            entity = new Associado();
        }else {
            entity = new Professor();
        }

        entity.setCpf(dto.getCpf());
        entity.setNome(dto.getNome());
        entity.setSenha(dto.getSenha());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setTipoUsuario(dto.getTipoUsuario());

        return (entityT) entity;
    }

}
