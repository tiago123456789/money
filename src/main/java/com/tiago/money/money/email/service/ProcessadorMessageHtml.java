package com.tiago.money.money.email.service;

import com.tiago.money.money.email.bean.MessageHtmlBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Component
public class ProcessadorMessageHtml {

    @Autowired
    private TemplateEngine thymeleaf;
    private Locale localeDefault = new Locale("pt", "BR");

    public String processar(MessageHtmlBean messageHtml) {
        Context context = new Context();
        context.setLocale(localeDefault);
        messageHtml
                .getParametros()
                .entrySet().forEach(item -> context.setVariable(item.getKey(), item.getValue()));
        return this.thymeleaf.process(messageHtml.getTemplateHtml(), context);
    }

}
