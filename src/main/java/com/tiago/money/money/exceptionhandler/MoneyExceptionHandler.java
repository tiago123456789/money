package com.tiago.money.money.exceptionhandler;

import com.tiago.money.money.exception.ErrorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class MoneyExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, this.getError(ex, status),  headers, status, request);
    }


    private ErrorDetail getError(Exception exception, HttpStatus statusError) {
        ErrorDetail error = new ErrorDetail();
        error.setCause(exception.getCause().toString());
        error.setMessageDesenvolvedor(exception.getMessage());
        error.setStatus(statusError.value());
        error.setTimeStamp(System.currentTimeMillis());
        error.setMessageUsuario(
                this.messageSource.getMessage("message.usuario.unkown-properties", null, this.getLocale()));
        return error;
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
