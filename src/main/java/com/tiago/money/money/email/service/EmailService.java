package com.tiago.money.money.email.service;

import com.tiago.money.money.bo.LancamentoBO;
import com.tiago.money.money.config.property.MoneyProperty;
import com.tiago.money.money.email.bean.EmailBean;
import com.tiago.money.money.email.bean.MessageHtmlBean;
import com.tiago.money.money.model.Lancamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MoneyProperty moneyProperty;

    @Autowired
    private ProcessadorMessageHtml processadorMessageHtml;

    @Autowired
    private EmailBean email;

    @Autowired
    private MessageHtmlBean messageHtmlBean;

    @Autowired
    private LancamentoBO lancamentoBO;


    @EventListener
    public void teste(ApplicationReadyEvent event) {
        Map<String, Object> parametrosTemplate = new HashMap<>();

        this.email.setSubject("Email teste!");
        this.email.setRemetente(this.moneyProperty.getEmail().getUsername());
        this.email.setDestinatarios(Arrays.asList("tiagorosadacost@gmail.com"));

        List<Lancamento> lancamentos = this.lancamentoBO.findAll();
        parametrosTemplate.put("lancamentos", lancamentos);

        this.messageHtmlBean.setTemplateHtml("email/aviso-lancamento-vencido");
        this.messageHtmlBean.setParametros(parametrosTemplate);

        this.enviar(this.email, this.messageHtmlBean);
    }

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
