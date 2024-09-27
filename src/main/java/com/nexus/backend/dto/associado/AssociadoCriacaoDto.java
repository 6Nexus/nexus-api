package com.nexus.backend.dto.associado;

import com.nexus.backend.dto.usuario.UsuarioDto;
import com.nexus.backend.enums.TipoUsuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AssociadoCriacaoDto extends UsuarioDto {

    @NotBlank
    private String endereco;
    private String grauParentescoComDesaparecido;

}
