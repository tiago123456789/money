package com.tiago.money.money.email.service;

import com.tiago.money.money.config.profile.MoneyProfile;
import com.tiago.money.money.email.bean.EmailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MoneyProfile moneyProfile;

    @Autowired
    private EmailBean email;

    @EventListener
    public void teste(ApplicationReadyEvent event) {
        this.email.setSubject("Email teste!");
        this.email.setRemetente(this.moneyProfile.getEmail().getUsername());
        this.email.setDestinatarios(Arrays.asList("tiagorosadacost@gmail.com"));
        this.email.setMessage("<h1>Email teste!</h1><p>Email é apenas um teste.</p>");
        this.enviar(this.email);
    }

    public void enviar(EmailBean email) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            mailHelper.setFrom(email.getRemetente());
            mailHelper.setTo(
                    email.getDestinatarios()
                            .toArray(new String[email.getDestinatarios().size()])
            );
            mailHelper.setSubject(email.getSubject());
            mailHelper.setText(email.getMessage(), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
