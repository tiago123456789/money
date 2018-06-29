package com.tiago.money.money.email.service;

import com.tiago.money.money.email.bean.EmailBean;
import com.tiago.money.money.email.bean.MessageHtmlBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProcessadorMessageHtml processadorMessageHtml;

    public void enviar(EmailBean email, MessageHtmlBean messageHtmlBean) {
        email.setMessage(this.processadorMessageHtml.processar(messageHtmlBean));
        this.enviar(email);
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
