package com.nexus.backend.mappers.curso.questionario.questionario;

import com.nexus.backend.dto.curso.questionario.questionario.QuestionarioCriacaoDto;
import com.nexus.backend.dto.curso.questionario.questionario.QuestionarioRespostaDto;
import com.nexus.backend.entities.curso.questionario.Pergunta;
import com.nexus.backend.entities.curso.questionario.Questionario;
import com.nexus.backend.entities.curso.questionario.Resposta;

import java.util.List;

public class QuestionarioMapper {
    public static Questionario toEntidadeDto(QuestionarioCriacaoDto dto) {
        if (dto == null) return null;

        return Questionario.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .build();
    }

    public static QuestionarioRespostaDto toRespostaDto(Questionario questionarioEncontrado, Pergunta perguntaEncontrada, List<Resposta> respostasEncontradas) {
        if (questionarioEncontrado == null || perguntaEncontrada == null || respostasEncontradas.isEmpty()) return null;

        List<QuestionarioRespostaDto.PerguntaDto.RespostaDto> respostasDto =
            respostasEncontradas.stream()
                    .map(respostaDto -> {
                        QuestionarioRespostaDto.PerguntaDto.RespostaDto respostaDtoMapeada = new QuestionarioRespostaDto.PerguntaDto.RespostaDto();
                        respostaDtoMapeada.setResposta(respostaDto.getResposta());
                        if (respostaDto.equals(perguntaEncontrada.getRespostaCerta())) respostaDtoMapeada.setRespostaCerta(true);
                        else respostaDtoMapeada.setRespostaCerta(false);

                        return respostaDtoMapeada;
                    })
                    .toList();

        QuestionarioRespostaDto.PerguntaDto perguntaDto =
                QuestionarioRespostaDto.PerguntaDto.builder()
                        .pergunta(perguntaEncontrada.getPergunta())
                        .respostas(respostasDto)
                        .build();

        return QuestionarioRespostaDto.builder()
                .titulo(questionarioEncontrado.getTitulo())
                .descricao(questionarioEncontrado.getDescricao())
                .pergunta(perguntaDto)
                .build();
    }
}
