package com.rshop.usuario.service.impl;



import com.rshop.usuario.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void enviarEmailConfirmacao(String email, String token) {
        String confirmacaoUrl = "http://localhost:3000/confirmar-email?token=" + token;

        Context context = new Context();
        context.setVariable("confirmacaoUrl", confirmacaoUrl);
        context.setVariable("email", email);

        String htmlContent = templateEngine.process("email-confirmacao", context);
        enviarEmail(email, "Confirme seu email - RSHop", htmlContent);
    }

    @Override
    public void enviarEmailRecuperacaoSenha(String email, String token) {
        String recuperacaoUrl = "http://localhost:3000/redefinir-senha?token=" + token;

        Context context = new Context();
        context.setVariable("recuperacaoUrl", recuperacaoUrl);
        context.setVariable("email", email);

        String htmlContent = templateEngine.process("email-recuperacao-senha", context);
        enviarEmail(email, "Recuperação de Senha - RSHop", htmlContent);
    }

    @Override
    public void enviarEmailBemVindo(String email, String nome) {
        Context context = new Context();
        context.setVariable("nome", nome);
        context.setVariable("email", email);

        String htmlContent = templateEngine.process("email-bemvindo", context);
        enviarEmail(email, "Bem-vindo à RSHop!", htmlContent);
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
            throw new RuntimeException("Erro ao enviar email", e);
        }
    }
}
