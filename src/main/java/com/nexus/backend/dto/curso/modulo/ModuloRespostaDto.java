package com.nexus.backend.dto.curso.modulo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuloRespostaDto {
    private Integer id;
    private String titulo;
    private String descricao;
    private Integer ordem;
    private LocalDateTime criadoEm;

}
