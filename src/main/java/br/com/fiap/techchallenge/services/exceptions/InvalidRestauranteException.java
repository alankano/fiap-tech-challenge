package br.com.fiap.techchallenge.services.exceptions;

public class InvalidRestauranteException extends RuntimeException {
    public InvalidRestauranteException(String message) {
        super(message);
    }
}
