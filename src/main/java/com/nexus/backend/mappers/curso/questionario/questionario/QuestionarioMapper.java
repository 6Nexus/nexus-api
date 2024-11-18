package com.nexus.backend.mappers.curso.questionario.questionario;

import com.nexus.backend.dto.curso.questionario.questionario.QuestionarioCriacaoDto;
import com.nexus.backend.dto.curso.questionario.questionario.QuestionarioRespostaDto;
import com.nexus.backend.entities.curso.questionario.Pergunta;
import com.nexus.backend.entities.curso.questionario.Questionario;
import com.nexus.backend.entities.curso.questionario.Resposta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionarioMapper {
    public static Questionario toEntidade(QuestionarioCriacaoDto dto) {
        if (dto == null) return null;

        return Questionario.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .build();
    }

    public static QuestionarioRespostaDto toRespostaDto(Questionario questionarioEncontrado, List<Pergunta> perguntasEncontradas, List<Resposta> respostasEncontradas) {
        if (questionarioEncontrado == null || perguntasEncontradas.isEmpty() || respostasEncontradas.isEmpty()) return null;

        List<QuestionarioRespostaDto.PerguntaDto> perguntasDto = new ArrayList<>();
        perguntasEncontradas.forEach(pergunta -> {
            List<Resposta> respostasDaPergunta = filtrarPorPergunta(respostasEncontradas, pergunta.getId());
            List<QuestionarioRespostaDto.PerguntaDto.RespostaDto> respostasMapeadas = mapearRespostastoResposta(respostasDaPergunta, pergunta.getRespostaCerta().getId());

            perguntasDto.add(
                QuestionarioRespostaDto.PerguntaDto.builder()
                        .pergunta(pergunta.getPergunta())
                        .respostas(respostasMapeadas)
                        .build()
            );
        });


        return QuestionarioRespostaDto.builder()
                .id(questionarioEncontrado.getId())
                .titulo(questionarioEncontrado.getTitulo())
                .descricao(questionarioEncontrado.getDescricao())
                .perguntas(perguntasDto)
                .build();
    }

    private static List<Resposta> filtrarPorPergunta(List<Resposta> respostas, Integer idPergunta) {
        return respostas.stream()
                .filter(resposta -> resposta.getPergunta().getId().equals(idPergunta))
                .collect(Collectors.toList());
    }

    private static List<QuestionarioRespostaDto.PerguntaDto.RespostaDto> mapearRespostastoResposta(List<Resposta> respostas, Integer idRespostaCerta) {
        List<QuestionarioRespostaDto.PerguntaDto.RespostaDto> respostasMapeadas = new ArrayList<>();
        respostas.forEach(resposta -> {
            respostasMapeadas.add(
                    QuestionarioRespostaDto.PerguntaDto.RespostaDto.builder()
                            .resposta(resposta.getResposta())
                            .respostaCerta(resposta.getId().equals(idRespostaCerta))
                            .build()
            );
        });
        return respostasMapeadas;
    }
}
