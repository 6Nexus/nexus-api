package com.nexus.backend.dto.curso.curso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoAssociadoRespostaDto {
    private Integer id;
    private String titulo;
    private String categoria;
    private String descricao;
    private Integer professorId;
    private String professorNome;
    private Boolean curtido;
}
