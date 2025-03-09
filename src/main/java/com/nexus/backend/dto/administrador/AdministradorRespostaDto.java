package com.nexus.backend.dto.administrador;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdministradorRespostaDto {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String cargo;
}
