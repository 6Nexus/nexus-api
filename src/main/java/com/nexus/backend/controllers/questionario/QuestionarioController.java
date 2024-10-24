package com.nexus.backend.controllers.questionario;

import com.nexus.backend.mappers.questionario.pergunta.PerguntaMapper;
import com.nexus.backend.dto.questionario.questionario.QuestionarioCriacaoDto;
import com.nexus.backend.mappers.questionario.questionario.QuestionarioMapper;
import com.nexus.backend.dto.questionario.questionario.QuestionarioRespostaDto;
import com.nexus.backend.mappers.questionario.resposta.RespostaMapper;
import com.nexus.backend.entities.questionario.Pergunta;
import com.nexus.backend.entities.questionario.Questionario;
import com.nexus.backend.entities.questionario.Resposta;
import com.nexus.backend.service.questionario.PerguntaService;
import com.nexus.backend.service.questionario.QuestionarioService;
import com.nexus.backend.service.questionario.RespostaService;
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

        Questionario questionarioEntidade = QuestionarioMapper.toEntidadeDto(criacaoDto);
        Questionario questionarioSalvoNoBanco = questionarioService.criar(questionarioEntidade);

        Pergunta perguntaEntidade = PerguntaMapper.toEntidadeDto(criacaoDto.getPergunta());
        Pergunta perguntaSalvaNoBanco = perguntaService.criar(perguntaEntidade, questionarioSalvoNoBanco);

        criacaoDto.getPergunta().getRespostas()
            .forEach(resposta -> {
                Resposta respostaEntidade = RespostaMapper.toEntidadeDto(resposta);
                Resposta respostaSalvaNoBanco = respostaService.criar(respostaEntidade, resposta.getRespostaCerta(), perguntaSalvaNoBanco);
            });

        return ResponseEntity.created(null).body(questionarioSalvoNoBanco.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionarioRespostaDto> buscarPorId(@PathVariable Integer id){
        Questionario questionarioEncontrado = questionarioService.buscarPorId(id);
        Pergunta perguntaEncontrada = perguntaService.buscarPorId(questionarioEncontrado.getId());
        List<Resposta> respostasEncontradas = respostaService.buscarPorId(perguntaEncontrada.getId());

        if (respostasEncontradas.isEmpty()) return ResponseEntity.noContent().build();

        QuestionarioRespostaDto dtoResposta = QuestionarioMapper.toRespostaDto(
                questionarioEncontrado, perguntaEncontrada, respostasEncontradas
        );

        return ResponseEntity.ok(dtoResposta);
    }
}
