package com.tiago.money.money.email.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class MessageHtmlBean implements Serializable {

    private String templateHtml;
    private Map<String, Object> parametros;

    public String getTemplateHtml() {
        return templateHtml;
    }

    public void setTemplateHtml(String templateHtml) {
        this.templateHtml = templateHtml;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
    }
}
