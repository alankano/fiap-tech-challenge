package br.com.fiap.techchallenge.services.exceptions;

public class InvalidUsuarioException extends RuntimeException {
    public InvalidUsuarioException(String message) {
        super(message);
    }
}
