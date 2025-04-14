package com.nexus.backend.mappers.curso;

import com.nexus.backend.dto.curso.curso.CursoAssociadoRespostaDto;
import com.nexus.backend.dto.curso.curso.CursoCriacaoDto;
import com.nexus.backend.dto.curso.curso.CursoRespostaDto;
import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.entities.curso.Curtida;

public class CursoMapper {
    public static Curso toEntidade(CursoCriacaoDto dto) {
        if (dto == null) return null;

        return Curso.builder()
                .titulo(dto.getTitulo())
                .categoria(dto.getCategoria())
                .descricao(dto.getDescricao())
                .build();
    }

    public static CursoRespostaDto toRespostaDto(Curso curso) {
        if (curso == null) return null;

        return CursoRespostaDto.builder()
                .id(curso.getId())
                .titulo(curso.getTitulo())
                .categoria(curso.getCategoria())
                .descricao(curso.getDescricao())
                .professorId(curso.getProfessor().getId())
                .professorNome(curso.getProfessor().getNome())
                .build();
    }

    public static CursoAssociadoRespostaDto toRespostaAssociadoDto(Curso curso, Boolean curtido) {
        if (curso == null) return null;

        return CursoAssociadoRespostaDto.builder()
                .id(curso.getId())
                .titulo(curso.getTitulo())
                .categoria(curso.getCategoria())
                .descricao(curso.getDescricao())
                .professorId(curso.getProfessor().getId())
                .professorNome(curso.getProfessor().getNome())
                .curtido(curtido)
                .build();
    }

    public static CursoAssociadoRespostaDto toRespostaAssociadoDto(Curtida curtida) {
        if (curtida == null) return null;

        return CursoAssociadoRespostaDto.builder()
                .id(curtida.getCurso().getId())
                .titulo(curtida.getCurso().getTitulo())
                .categoria(curtida.getCurso().getCategoria())
                .descricao(curtida.getCurso().getDescricao())
                .professorId(curtida.getCurso().getProfessor().getId())
                .professorNome(curtida.getCurso().getProfessor().getNome())
                .curtido(true)
                .build();
    }
}
