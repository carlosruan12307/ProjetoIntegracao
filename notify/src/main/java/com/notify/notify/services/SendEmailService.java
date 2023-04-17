package com.notify.notify.services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.notify.notify.models.EmailModel;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SendEmailService {
    private final JavaMailSender javaMailSender;

    public SendEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviar(EmailModel eModel) {
        log.info("Enviando email simples");
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(eModel.getEmailFrom());
            mensagem.setTo(eModel.getEmailTo());

            mensagem.setSubject(eModel.getSubject());
            mensagem.setText(eModel.getText());

            javaMailSender.send(mensagem);
            log.info("Email enviado com sucesso!");
        } catch (MailException e) {
            System.out.println(e);
        }

    }

    public void enviarEmailComAnexo(String para, String titulo, String conteudo, String arquivo)
            throws MessagingException {
        log.info("Enviando email com anexo");
        MimeMessage mensagem = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);

        helper.setTo(para);
        helper.setSubject(titulo);
        helper.setText(conteudo, true);

        helper.addAttachment("image1.jpeg", new ClassPathResource(arquivo));

        javaMailSender.send(mensagem);
        log.info("Email com anexo enviado com sucesso.");
    }
}
