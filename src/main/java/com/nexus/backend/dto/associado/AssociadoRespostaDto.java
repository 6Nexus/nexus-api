package com.nexus.backend.dto.associado;

import com.nexus.backend.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssociadoRespostaDto {
    private Integer id;
    @NotBlank
    private String nome;

    @Email
    private String email;

//    @NotBlank
////    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")
//    private String cpf;
    private String telefone;

//    //    @Enumerated(EnumType.STRING)
//    private TipoUsuario tipoUsuario;

    @NotBlank
    private String endereco;
    private String grauParentescoComDesaparecido;
}
