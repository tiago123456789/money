package com.tiago.money.money.exception;

public class NaoEncontradoException extends RuntimeException {

    public NaoEncontradoException(String message) {
        super(message);
    }

    public NaoEncontradoException(Throwable cause) {
        super(cause);
    }

}
