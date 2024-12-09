package com.nexus.backend.mappers.curso;

import com.nexus.backend.dto.curso.certificado.CertificadoRespostaDto;
import com.nexus.backend.dto.curso.curso.CursoRespostaDto;
import com.nexus.backend.entities.curso.Certificado;

public class CertificadoMapper {
    public static CertificadoRespostaDto toRespostaDto(Certificado certificado) {
        if (certificado == null) return null;

        CursoRespostaDto cursoReposta = CursoRespostaDto.builder()
                .id(certificado.getCurso().getId())
                .titulo(certificado.getCurso().getTitulo())
                .categoria(certificado.getCurso().getCategoria())
                .descricao(certificado.getCurso().getDescricao())
                .professorId(certificado.getCurso().getProfessor().getId())
                .professorNome(certificado.getCurso().getProfessor().getNome())
                .build();


        return CertificadoRespostaDto.builder()
                .dataConclusao(certificado.getDataConclusao())
                .curso(cursoReposta)
                .build();
    }
}
