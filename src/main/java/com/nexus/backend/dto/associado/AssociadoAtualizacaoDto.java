package com.nexus.backend.dto.associado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociadoAtualizacaoDto {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
}
