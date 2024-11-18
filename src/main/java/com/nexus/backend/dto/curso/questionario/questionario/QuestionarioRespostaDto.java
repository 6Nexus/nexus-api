package com.nexus.backend.dto.curso.questionario.questionario;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionarioRespostaDto {
    private Integer id;
    private String titulo;
    private String descricao;
    private List<QuestionarioRespostaDto.PerguntaDto> perguntas;

    @Data
    @Builder
    public static class PerguntaDto {
        private String pergunta;
        private List<QuestionarioRespostaDto.PerguntaDto.RespostaDto> respostas;

        @Data
        @Builder
        public static class RespostaDto {
            private String resposta;
            private Boolean respostaCerta;
        }
    }
}
