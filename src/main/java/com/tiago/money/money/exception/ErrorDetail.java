package com.tiago.money.money.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {

    private Integer status;
    private long timeStamp;
    private String messageUsuario;
    private String messageDesenvolvedor;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessageUsuario() {
        return messageUsuario;
    }

    public void setMessageUsuario(String messageUsuario) {
        this.messageUsuario = messageUsuario;
    }

    public String getMessageDesenvolvedor() {
        return messageDesenvolvedor;
    }

    public void setMessageDesenvolvedor(String messageDesenvolvedor) {
        this.messageDesenvolvedor = messageDesenvolvedor;
    }
}
