package com.nexus.backend.controllers.curso.questionario;

import com.nexus.backend.mappers.curso.questionario.pergunta.PerguntaMapper;
import com.nexus.backend.dto.curso.questionario.questionario.QuestionarioCriacaoDto;
import com.nexus.backend.mappers.curso.questionario.questionario.QuestionarioMapper;
import com.nexus.backend.dto.curso.questionario.questionario.QuestionarioRespostaDto;
import com.nexus.backend.mappers.curso.questionario.resposta.RespostaMapper;
import com.nexus.backend.entities.curso.questionario.Pergunta;
import com.nexus.backend.entities.curso.questionario.Questionario;
import com.nexus.backend.entities.curso.questionario.Resposta;
import com.nexus.backend.service.curso.questionario.PerguntaService;
import com.nexus.backend.service.curso.questionario.QuestionarioService;
import com.nexus.backend.service.curso.questionario.RespostaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questionarios")
@RequiredArgsConstructor
public class QuestionarioController {
    private final QuestionarioService questionarioService;
    private final PerguntaService perguntaService;
    private final RespostaService respostaService;


    @PostMapping
    public ResponseEntity<Integer> cadastrar(
            @RequestBody @Valid QuestionarioCriacaoDto criacaoDto) {

        Questionario questionarioEntidade = QuestionarioMapper.toEntidade(criacaoDto);
        Questionario questionarioSalvoNoBanco = questionarioService.cadastrar(questionarioEntidade, criacaoDto.getIdModulo());

        criacaoDto.getPerguntas()
                .forEach(pergunta -> {
                    Pergunta perguntaEntidade = PerguntaMapper.toEntidadeDto(pergunta);
                    Pergunta perguntaSalvaNoBanco = perguntaService.criar(perguntaEntidade, questionarioSalvoNoBanco);

                    pergunta.getRespostas()
                            .forEach(resposta -> {
                                Resposta respostaEntidade = RespostaMapper.toEntidadeDto(resposta);
                                respostaService.criar(respostaEntidade, resposta.getRespostaCerta(), perguntaSalvaNoBanco);
                            });
                });

        return ResponseEntity.created(null).body(questionarioSalvoNoBanco.getId());
    }

    @GetMapping("/modulo/{idModulo}")
    public ResponseEntity<QuestionarioRespostaDto> buscarPorModulo(@PathVariable Integer idModulo){
        Questionario questionarioEncontrado = questionarioService.buscarPorModulo(idModulo);

        List<Pergunta> perguntasEncontradas = perguntaService.buscarPorIdQuestionario(questionarioEncontrado.getId());
        if (perguntasEncontradas.isEmpty()) return ResponseEntity.noContent().build();
        List<Integer> idsPerguntasEncontradas = perguntaService.retornarIds(perguntasEncontradas);

        List<Resposta> respostasEncontradas = respostaService.buscarPorIdPerguntas(idsPerguntasEncontradas);
        if (respostasEncontradas.isEmpty()) return ResponseEntity.noContent().build();

        QuestionarioRespostaDto dtoResposta = QuestionarioMapper.toRespostaDto(
                questionarioEncontrado, perguntasEncontradas, respostasEncontradas
        );

        return ResponseEntity.ok(dtoResposta);
    }
}
