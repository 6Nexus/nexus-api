package com.nexus.backend.dto.professor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfessorRespostaDto {
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
//    private String telefone;
    private String areaAtuacao;
    private Boolean aprovado;

}
