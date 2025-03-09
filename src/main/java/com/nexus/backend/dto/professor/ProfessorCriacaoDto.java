package com.nexus.backend.dto.professor;

import com.nexus.backend.dto.usuario.UsuarioDto;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorCriacaoDto extends UsuarioDto {
    @NotBlank
    private String areaAtuacao;

    @AssertFalse
    private Boolean aprovado;
}
