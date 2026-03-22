package br.com.fiap.techchallenge.services.exceptions;

public class InvalidTipoUsuarioException extends RuntimeException {
    public InvalidTipoUsuarioException(String message) {
        super(message);
    }
}
