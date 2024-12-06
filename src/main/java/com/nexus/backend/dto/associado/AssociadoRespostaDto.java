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


    private String endereco;
    private String grauParentescoComDesaparecido;

    public AssociadoRespostaDto(Integer id, String nome, String email, String telefone, String endereco, String grauParentescoComDesaparecido) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.grauParentescoComDesaparecido = grauParentescoComDesaparecido;
    }
}
