package br.com.fiap.techchallenge.services.exceptions;

public class InvalidSenhaException extends RuntimeException {
    public InvalidSenhaException(String message) {
        super(message);
    }
}
