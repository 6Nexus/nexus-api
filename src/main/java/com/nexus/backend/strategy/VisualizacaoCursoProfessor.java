package com.nexus.backend.strategy;

import com.nexus.backend.dto.CursoDto;
import org.springframework.stereotype.Component;

import com.nexus.backend.dto.CursoDto;

public class VisualizacaoCursoProfessor implements VisualizacaoCursoStrategy {

    @Override
    public CursoDto visualizarCurso(Integer cursoId) {
        // Lógica de visualização específica para professor
        CursoDto cursoDto = new CursoDto();
        cursoDto.setNomeCurso("Curso para Professor");
        cursoDto.setDescricao("Descrição completa do curso para professores.");
        return cursoDto;
    }
}
