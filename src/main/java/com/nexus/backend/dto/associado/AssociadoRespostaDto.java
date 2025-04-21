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

    private String nome;

    private String email;


    private String telefone;

    private Boolean aprovado;
//
//    private String endereco;
//    private String grauParentescoComDesaparecido;
}
