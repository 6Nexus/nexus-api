package com.nexus.backend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursoDto {
    private String nomeCurso;
    private String descricao;
    private Integer cargaHoraria; // Opcional, para exemplo de professores

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
}