package com.nexus.backend.dto.curso.modulo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuloRespostaDto {
    private Integer id;
    private String titulo;
    private Integer ordem;
}
