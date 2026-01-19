package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.entities.Login;
import br.com.fiap.techchallenge.entities.Usuario;
import br.com.fiap.techchallenge.repositories.LoginRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Optional<Login> validaLogin(Login login) {
        return Optional.ofNullable(loginRepository.validaLogin(login)
                .orElseThrow(() -> new InvalidLoginException("Senha ou login inválido")));
    }
}
