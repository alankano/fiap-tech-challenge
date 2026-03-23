package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.entities.Login;
import br.com.fiap.techchallenge.repositories.LoginRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    @Mock
    private LoginRepository loginRepository;

    @InjectMocks
    private LoginService loginService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp(){
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void validaLogin_shouldThrow_whenNotFound() {
        Login l = new Login(); l.setLogin("x"); l.setSenha("y");
        when(loginRepository.validaLogin(l)).thenReturn(Optional.empty());
        assertThrows(InvalidLoginException.class, () -> loginService.validaLogin(l));
    }

    @Test
    void validaLogin_shouldReturnOptional_whenFound() {
        Login l = new Login(); l.setLogin("u"); l.setSenha("p");
        when(loginRepository.validaLogin(l)).thenReturn(Optional.of(l));
        var res = loginService.validaLogin(l);
        assertTrue(res.isPresent());
    }
}

