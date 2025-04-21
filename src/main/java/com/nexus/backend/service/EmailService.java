package com.nexus.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void enviarEmail(String destinatario, String assunto, String corpo, ByteArrayOutputStream pdfStream) throws MessagingException {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(corpo);

        InputStreamSource anexo = new ByteArrayResource(pdfStream.toByteArray());
        helper.addAttachment("certificado.pdf", anexo);

        mailSender.send(mensagem);
    }
}
