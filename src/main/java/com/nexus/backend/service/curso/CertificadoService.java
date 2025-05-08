package com.nexus.backend.service.curso;

import com.nexus.backend.entities.curso.Certificado;
import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.entities.curso.Matricula;
import com.nexus.backend.entities.curso.questionario.ProgressoQuestionario;
import com.nexus.backend.service.EmailService;
import com.nexus.backend.service.curso.questionario.ProgressoQuestionarioService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificadoService {
    private final ModuloService moduloService;
    private final ProgressoQuestionarioService progressoQuestionarioService;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;
    private final MatriculaService matriculaService;

    @Scheduled(cron = "0 30 0 * * *")
    public void enviarCertificados() {
        List<ProgressoQuestionario> progressosEnviadosOntem = progressoQuestionarioService.respondidosOntemComPontuacaoMaiorQue70();

        progressosEnviadosOntem.stream()
            .filter(progresso -> {
                    if (progresso.getMatricula().getCertificadoEmitido()) return false;

                    Integer cursoId = progresso.getMatricula().getCurso().getId();
                    Integer quantidadeModulos = moduloService.qtdModulosPorCurso(cursoId);
                    Integer quantidadeRespostas = progressoQuestionarioService.quantidadePorMatriculaComPontuacaoMaiorQue70(progresso.getMatricula().getId());

                    return quantidadeModulos == quantidadeRespostas;
            }).forEach(progresso -> {
                    try {
                        gerarCertificado(progresso.getMatricula());

                        matriculaService.atualizarEmissaoCertificado(progresso.getMatricula());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void gerarCertificado(Matricula matricula) throws Exception {
        String aluno = matricula.getAssociado().getNome();
        String professor = matricula.getCurso().getProfessor().getNome();
        String curso = matricula.getCurso().getTitulo();

        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = hoje.format(formatter);

        Context context = new Context();
        context.setVariable("nome", aluno);
        context.setVariable("professor", professor);
        context.setVariable("data", dataFormatada);
        context.setVariable("curso", curso);

        String html = templateEngine.process("certificado", context);

        String baseUri = new ClassPathResource("static/").getURL().toString();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withHtmlContent(html, baseUri);
        builder.toStream(outputStream);
        builder.run();

        String destinatario = matricula.getAssociado().getEmail();

        emailService.enviarEmail(
                destinatario,
                "Seu Certificado está pronto!",
                "Olá, segue em anexo o seu certificado.",
                outputStream
        );
    }
}
