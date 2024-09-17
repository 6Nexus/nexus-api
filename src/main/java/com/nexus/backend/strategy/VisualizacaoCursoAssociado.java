package com.nexus.backend.strategy;

import com.nexus.backend.dto.CursoDto;
import org.springframework.stereotype.Component;


import com.nexus.backend.dto.CursoDto;

public class VisualizacaoCursoAssociado implements VisualizacaoCursoStrategy {

    @Override
    public CursoDto visualizarCurso(Integer cursoId) {
        // Lógica de visualização específica para associado
        CursoDto cursoDto = new CursoDto();
        cursoDto.setNomeCurso("Curso para Associado");
        cursoDto.setDescricao("Descrição básica do curso para associados.");
        return cursoDto;
    }
}
