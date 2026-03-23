 package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.entities.Senha;
import br.com.fiap.techchallenge.repositories.SenhaRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidSenhaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SenhaServiceTest {

    @Mock
    private SenhaRepository senhaRepository;

    @InjectMocks
    private SenhaService senhaService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp(){
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateSenha_shouldThrow_whenCurrentMismatch() {
        Senha input = new Senha(); input.setSenha("old"); input.setNovaSenha("n"); input.setNovaSenha2("n");
        when(senhaRepository.findSenhaById(1L)).thenReturn(Optional.of(new Senha(1L, null, "different", null, null)));
        assertThrows(InvalidSenhaException.class, () -> senhaService.updateSenha(input, 1L));
    }

    @Test
    void updateSenha_shouldThrow_whenNewMismatch() {
        Senha input = new Senha(); input.setSenha("old"); input.setNovaSenha("n"); input.setNovaSenha2("m");
        when(senhaRepository.findSenhaById(1L)).thenReturn(Optional.of(new Senha(1L, null, "old", null, null)));
        assertThrows(InvalidSenhaException.class, () -> senhaService.updateSenha(input, 1L));
    }

    @Test
    void findSenhaById_shouldThrow_whenNotFound() {
        when(senhaRepository.findSenhaById(2L)).thenReturn(Optional.empty());
        assertThrows(InvalidSenhaException.class, () -> senhaService.findSenhaById(2L));
    }
}

