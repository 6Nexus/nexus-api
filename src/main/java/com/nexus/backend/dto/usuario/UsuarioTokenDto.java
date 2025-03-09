package com.nexus.backend.dto.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioTokenDto {
    private Integer userId;
    private String nome;
    private String email;
    private String token;
}
