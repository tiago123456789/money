package com.tiago.money.money.exception;

public class PessoaInativaException extends NegocioException {

    public PessoaInativaException(String message) {
        super(message);
    }

    public PessoaInativaException(String message, Throwable cause) {
        super(message, cause);
    }
}
