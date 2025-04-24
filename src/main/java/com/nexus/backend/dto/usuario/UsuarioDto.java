package com.nexus.backend.dto.usuario;

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
public class UsuarioDto {

    @NotBlank
    private String nome;

    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String senha;

//    @NotBlank
//    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")
//    private String cpf;
//    private String telefone;

//        @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

}
