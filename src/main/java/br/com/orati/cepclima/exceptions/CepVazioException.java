package br.com.orati.cepclima.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CepVazioException extends RuntimeException {
    public CepVazioException() {
        super();
    }

    public CepVazioException(String message) {
        super(message);
    }
}
