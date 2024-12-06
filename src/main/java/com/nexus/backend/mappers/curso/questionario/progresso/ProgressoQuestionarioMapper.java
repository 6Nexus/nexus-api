package com.nexus.backend.mappers.curso.questionario.progresso;

import com.nexus.backend.dto.curso.curso.CursoCriacaoDto;
import com.nexus.backend.dto.curso.curso.CursoRespostaDto;
import com.nexus.backend.dto.curso.questionario.progresso.ProgressoQuestionarioCriacaoDto;
import com.nexus.backend.dto.curso.questionario.progresso.ProgressoQuestionarioRespostaDto;
import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.entities.curso.questionario.ProgressoQuestionario;

public class ProgressoQuestionarioMapper {
    public static ProgressoQuestionario toEntidade(ProgressoQuestionarioCriacaoDto dto) {
        if (dto == null) return null;

        return ProgressoQuestionario.builder()
                .pontuacao(dto.getPontuacao())
                .build();
    }

    public static ProgressoQuestionarioRespostaDto toRespostaDto(ProgressoQuestionario progresso) {
        if (progresso == null) return null;

        return ProgressoQuestionarioRespostaDto.builder()
                .id(progresso.getId())
                .pontuacao(progresso.getPontuacao())
                .matriculaId(progresso.getMatricula().getId())
                .questionarioId(progresso.getQuestionario().getId())
                .build();
    }
}
