package com.admeliora.briefbot.adapter.out.email;

import com.admeliora.briefbot.application.user.port.out.EmailPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Email adapter using Spring Mail with Thymeleaf templates
 * Active for all profiles except 'test'
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailAdapter implements EmailPort {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.properties.mail.from}")
    private String fromAddress;

    @Override
    public void sendTemporaryPassword(String to, String givenName, String temporaryPassword) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject("Welcome to BriefBot - Your Temporary Password");

            // Build email body from Thymeleaf template
            String emailBody = buildEmailBodyFromTemplate(givenName, temporaryPassword);
            helper.setText(emailBody, true); // true = HTML

            mailSender.send(mimeMessage);
            log.info("Temporary password email sent to: {} from: {}", to, fromAddress);
        } catch (MessagingException e) {
            log.error("Failed to create email message for: {} from: {}", to, fromAddress, e);
            throw new RuntimeException("Failed to send email", e);
        } catch (Exception e) {
            log.error("Failed to send email to: {} from: {}", to, fromAddress, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String buildEmailBodyFromTemplate(String givenName, String temporaryPassword) {
        Context context = new Context();
        context.setVariable("givenName", givenName);
        context.setVariable("temporaryPassword", temporaryPassword);
        context.setVariable("year", java.time.Year.now().getValue());

        return templateEngine.process("email/temporary-password", context);
    }
}

