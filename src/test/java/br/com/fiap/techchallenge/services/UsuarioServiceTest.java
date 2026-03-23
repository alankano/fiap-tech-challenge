package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.entities.Usuario;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidUsuarioException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) mocks.close();
    }

    @Test
    void saveUsuario_shouldThrow_whenEmailExists() {
        Usuario u = new Usuario(); u.setEmail("a@b.com");
        when(usuarioRepository.findUsuarioByEmail(u)).thenReturn(Optional.of(new Usuario()));
        assertThrows(InvalidUsuarioException.class, () -> usuarioService.saveUsuario(u));
    }

    @Test
    void saveUsuario_shouldSave_whenValid() {
        Usuario u = new Usuario();
        u.setNome("n"); u.setEmail("e"); u.setLogin("l"); u.setSenha("s"); u.setEndereco("en"); u.setTipoUsuario("t");
        when(usuarioRepository.findUsuarioByEmail(u)).thenReturn(Optional.empty());
        when(usuarioRepository.saveUsuario(u)).thenReturn(1);

        assertDoesNotThrow(() -> usuarioService.saveUsuario(u));
        verify(usuarioRepository).saveUsuario(u);
    }

    @Test
    void updateUsuario_shouldThrow_whenEmailConflict() {
        Usuario u = new Usuario(); u.setEmail("e");
        Usuario existing = new Usuario(); existing.setId(3L); existing.setEmail("e");
        when(usuarioRepository.findUsuarioByEmail(u)).thenReturn(Optional.of(existing));
        assertThrows(InvalidUsuarioException.class, () -> usuarioService.updateUsuario(u, 2L));
    }

    @Test
    void findByNomeUsuario_shouldReturnList() {
        when(usuarioRepository.findUsuarioByNome("x")).thenReturn(List.of(new Usuario()));
        var r = usuarioService.findByNomeUsuario("x");
        assertFalse(r.isEmpty());
    }

    @Test
    void findByIdUsuario_shouldThrow_whenNotFound() {
        when(usuarioRepository.findUsuarioById(5L)).thenReturn(Optional.empty());
        assertThrows(InvalidUsuarioException.class, () -> usuarioService.findByIdUsuario(5L));
    }

    @Test
    void findAllUsuario_shouldReturnList() {
        when(usuarioRepository.findUsuarioAll(10, 0)).thenReturn(List.of(new Usuario()));
        var res = usuarioService.findAllUsuario(1, 10);
        assertFalse(res.isEmpty());
    }

    @Test
    void updateUsuario_shouldUpdate_whenNoConflict() {
        Usuario u = new Usuario(); u.setEmail(null);
        when(usuarioRepository.updateUsuario(u, 2L)).thenReturn(1);
        assertDoesNotThrow(() -> usuarioService.updateUsuario(u, 2L));
    }

    @Test
    void deleteUsuario_shouldDelete_whenSuccess() {
        when(usuarioRepository.deleteUsuario(7L)).thenReturn(1);
        assertDoesNotThrow(() -> usuarioService.deleteUsuario(7L));
    }

    @Test
    void findByEmail_shouldReturnOptional() {
        Usuario u = new Usuario(); u.setEmail("a@b.com");
        when(usuarioRepository.findUsuarioByEmail(u)).thenReturn(Optional.of(u));
        var res = usuarioService.findByEmail(u);
        assertTrue(res.isPresent());
    }

}
