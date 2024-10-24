package com.nexus.backend.dto.questionario.questionario;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionarioCriacaoDto {
    private String titulo;
    private String descricao;
    private PerguntaDto pergunta;

    @Data
    @Builder
    public static class PerguntaDto {
        private String pergunta;
        private List<RespostaDto> respostas;

        @Data
        @Builder
        public static class RespostaDto {
            private String resposta;
            private Boolean respostaCerta;
        }
    }
}
