package com.rshop.usuario.service.impl;



import com.rshop.usuario.service.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void enviarEmailConfirmacao(String email, String token) {
        String assunto = "Confirme seu email - RSHop";
        String template = "email-confirmacao";

        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("email", email);

        enviarEmailComTemplate(email, assunto, template, context);
    }

    @Override
    public void enviarEmailRecuperacaoSenha(String email, String token) {
        String assunto = "Recuperação de Senha - RSHop";
        String template = "email-recuperacao-senha";

        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("email", email);

        enviarEmailComTemplate(email, assunto, template, context);
    }

    @Override
    public void enviarEmailBemVindo(String email, String nome) {
        String assunto = "Bem-vindo à RSHop!";
        String template = "email-bemvindo";

        Context context = new Context();
        context.setVariable("nome", nome);
        context.setVariable("email", email);

        enviarEmailComTemplate(email, assunto, template, context);
    }

    private void enviarEmailComTemplate(String to, String subject, String template, Context context) {
        try {
            String htmlContent = templateEngine.process(template, context);
            enviarEmail(to, subject, htmlContent);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar template de email: " + e.getMessage(), e);
        }
    }

    private void enviarEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar email para: " + to, e);
        }
    }
}