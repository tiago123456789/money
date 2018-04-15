package com.tiago.money.money.exceptionhandler;

import com.tiago.money.money.builder.ErrorDetailBuilder;
import com.tiago.money.money.exception.ErrorDetail;
import com.tiago.money.money.exception.NaoEncontradoException;
import com.tiago.money.money.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class MoneyExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, Arrays.asList(this.getError(ex, status)),  headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(ex, this.getErrors(ex.getBindingResult()), headers, status, request);
    }

    @ExceptionHandler(value = { NegocioException.class })
    protected ResponseEntity<List<ErrorDetail>> handleNegocioException(NegocioException exception) {
        ErrorDetail erro = new ErrorDetailBuilder()
                .comMessageDesenvolvedor(exception.getMessage())
                .comMessageUsuario(exception.getMessage())
                .comStatus(HttpStatus.CONFLICT.value())
                .comTimeStamp(System.currentTimeMillis())
                .getInstance();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Arrays.asList(erro));
    }

    @ExceptionHandler(value = NaoEncontradoException.class )
    protected ResponseEntity<List<ErrorDetail>> handleNaoEncontradoException(NaoEncontradoException exception) {
        ErrorDetail erro = new ErrorDetailBuilder()
                .comTimeStamp(System.currentTimeMillis())
                .comStatus(HttpStatus.NOT_FOUND.value())
                .comMessageDesenvolvedor(exception.getMessage())
                .comMessageUsuario(exception.getMessage())
                .getInstance();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Arrays.asList(erro));
    }

    private List<ErrorDetail> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> {
                    return new ErrorDetailBuilder()
                            .comMessageDesenvolvedor(fieldError.toString())
                            .comStatus(HttpStatus.BAD_REQUEST.value())
                            .comTimeStamp(System.currentTimeMillis())
                            .comMessageUsuario(this.messageSource.getMessage(fieldError, this.getLocale()))
                            .getInstance();
                }).collect(Collectors.toList());
    }

    private ErrorDetail getError(Exception exception, HttpStatus statusError) {
        return new ErrorDetailBuilder()
                .comMessageDesenvolvedor(exception.getMessage())
                .comStatus(statusError.value())
                .comTimeStamp(System.currentTimeMillis())
                .comMessageUsuario(this.getMessage("message.usuario.unkown-properties"))
                .getInstance();
    }

    private String getMessage(String chave) {
        return this.messageSource.getMessage(chave, null, this.getLocale());
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
