package br.com.orati.cepclima.exceptions;

public class CepInvalidoException extends RuntimeException {

    public CepInvalidoException() {
        super();
    };

    public CepInvalidoException(String message) {
        super(message);
    };
}
