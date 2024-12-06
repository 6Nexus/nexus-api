package com.nexus.backend.dto.curso.questionario.progresso;

import com.nexus.backend.entities.curso.Matricula;
import com.nexus.backend.entities.curso.questionario.Questionario;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressoQuestionarioRespostaDto {
    private Integer id;
    private Double pontuacao;
    private Integer matriculaId;
    private Integer questionarioId;
}
