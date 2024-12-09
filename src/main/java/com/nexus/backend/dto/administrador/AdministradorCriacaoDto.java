package com.nexus.backend.dto.administrador;

import com.nexus.backend.dto.usuario.UsuarioDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorCriacaoDto extends UsuarioDto {
    @NotBlank
    private String cargo;
}
