package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Login;

import java.util.Optional;


public interface LoginRepository {

    Optional<Login> validaLogin(Login login);

}
