package com.nexus.backend.controllers.questionary;

import com.nexus.backend.dto.questionary.pergunta.PerguntaMapper;
import com.nexus.backend.dto.questionary.questionario.QuestionarioCriacaoDto;
import com.nexus.backend.dto.questionary.questionario.QuestionarioMapper;
import com.nexus.backend.dto.questionary.questionario.QuestionarioRespostaDto;
import com.nexus.backend.dto.questionary.resposta.RespostaMapper;
import com.nexus.backend.entities.questionary.Pergunta;
import com.nexus.backend.entities.questionary.Questionario;
import com.nexus.backend.entities.questionary.Resposta;
import com.nexus.backend.service.questionary.PerguntaService;
import com.nexus.backend.service.questionary.QuestionarioService;
import com.nexus.backend.service.questionary.RespostaService;
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
