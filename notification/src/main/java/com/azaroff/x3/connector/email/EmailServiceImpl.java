package com.azaroff.x3.connector.email;

import com.azaroff.x3.connector.email.dao.entity.Email;
import com.azaroff.x3.connector.email.dao.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Stream;

@Component("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;
    @Qualifier("emailRepository")
    @Autowired
    private EmailRepository emailRepository;

    private static Function<SimpleMailMessage, Email> CONVERT_TO_EMAIL_ENTITY = o -> {
        Email e = new Email();
        e.setSubject(o.getSubject());
        e.setContent(o.getText());
        e.setTo(Stream.of(o.getTo()).findFirst().orElse("undefined"));
        return e;
    };

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        emailRepository.save(CONVERT_TO_EMAIL_ENTITY.apply(message));
    }
}