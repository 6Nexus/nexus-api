package com.nexus.backend.mappers;

import com.nexus.backend.dto.administrador.AdministradorCriacaoDto;
import com.nexus.backend.dto.administrador.AdministradorRespostaDto;
import com.nexus.backend.entities.Administrador;
import com.nexus.backend.enums.TipoUsuario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class AdministradorMapper extends UsuarioMapper<Administrador, AdministradorCriacaoDto> {
    public AdministradorRespostaDto toRespostaDto (Administrador entity){
        if (entity == null) return null;

        return AdministradorRespostaDto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .cargo(entity.getCargo())
                .build();
    }

    public Administrador toCriacaoEntity (@Valid AdministradorCriacaoDto dto){
        if (dto==null) return null;
        Administrador admin = super.toEntity(dto);
        admin.setTipoUsuario(TipoUsuario.ADMIN);
        admin.setCargo(dto.getCargo());
        return admin;
    }
}
