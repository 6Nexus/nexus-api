package com.nexus.backend.mappers;

import com.nexus.backend.dto.associado.AssociadoAtualizacaoDto;
import com.nexus.backend.dto.associado.AssociadoCriacaoDto;
import com.nexus.backend.dto.associado.AssociadoRespostaDto;
import com.nexus.backend.entities.Associado;
import com.nexus.backend.enums.TipoUsuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AssociadoMapper extends UsuarioMapper<Associado, AssociadoCriacaoDto>{

    public AssociadoRespostaDto toRespostaDto(Associado entity){
        if (entity==null) return null;

        return AssociadoRespostaDto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
//                .endereco(entity.getEndereco())
                .telefone(entity.getTelefone())
                .aprovado(entity.getAprovado())
//                .grauParentescoComDesaparecido(entity.getGrauParentescoComDesaparecido())
                .build();
    }

    public Associado toCriacaoEntity(@Valid AssociadoCriacaoDto dto){
        if (dto==null) return null;

        Associado associado = super.toEntity(dto);
        associado.setTelefone(dto.getTelefone());
        associado.setTipoUsuario(TipoUsuario.ASSOCIADO);
//        associado.setGrauParentescoComDesaparecido(dto.getGrauParentescoComDesaparecido());
//        associado.setEndereco(dto.getEndereco());
        associado.setAprovado(false);
        return associado;
    }

    public Associado toAtualizacaoEntity(@Valid AssociadoAtualizacaoDto dto, Associado a, PasswordEncoder passwordEncoder){
        if (dto==null) return null;

        if (dto.getNome() != null ) a.setNome(dto.getNome());
        if (dto.getEmail() != null) a.setEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) a.setSenha(passwordEncoder.encode(dto.getSenha()));

       return a;
    }
}
