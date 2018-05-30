package com.tiago.money.money.builder;

import com.tiago.money.money.exception.ErrorDetail;

public class ErrorDetailBuilder implements Builder<ErrorDetail> {

    private Integer status;
    private long timeStamp;
    private String messageUsuario;
    private String messageDesenvolvedor;

    public ErrorDetailBuilder comStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ErrorDetailBuilder comTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public ErrorDetailBuilder comMessageUsuario(String messageUsuario) {
        this.messageUsuario = messageUsuario;
        return this;
    }

    public ErrorDetailBuilder comMessageDesenvolvedor(String messageDesenvolvedor) {
        this.messageDesenvolvedor = messageDesenvolvedor;
        return this;
    }

    @Override
    public ErrorDetail getInstance() {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setMessageDesenvolvedor(this.messageDesenvolvedor);
        errorDetail.setMessageUsuario(this.messageUsuario);
        errorDetail.setTimeStamp(this.timeStamp);
        errorDetail.setStatus(this.status);
        return errorDetail;
    }
}
