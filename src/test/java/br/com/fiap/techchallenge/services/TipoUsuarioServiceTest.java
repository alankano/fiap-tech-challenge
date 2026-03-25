package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.dto.UpdateTipoUsuarioRecord;
import br.com.fiap.techchallenge.entities.TipoUsuario;
import br.com.fiap.techchallenge.repositories.TipoUsuarioRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidTipoUsuarioException;
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

class TipoUsuarioServiceTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @InjectMocks
    private TipoUsuarioService tipoUsuarioService;

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
    void criar_shouldSave_whenTipoUnique() {
        TipoUsuario t = new TipoUsuario();
        t.setTipo("Cliente");

        when(tipoUsuarioRepository.existsByTipo("Cliente")).thenReturn(false);
        when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenAnswer(invocation -> {
            TipoUsuario arg = invocation.getArgument(0);
            arg.setId(1L);
            return arg;
        });

        TipoUsuario saved = tipoUsuarioService.criar(t);
        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        verify(tipoUsuarioRepository).save(any(TipoUsuario.class));
    }

    @Test
    void criar_shouldThrow_whenTipoDuplicate() {
        TipoUsuario t = new TipoUsuario();
        t.setTipo("Admin");

        when(tipoUsuarioRepository.existsByTipo("Admin")).thenReturn(true);

        assertThrows(InvalidTipoUsuarioException.class, () -> tipoUsuarioService.criar(t));
    }

    @Test
    void atualizar_shouldUpdate_whenExists() {
        TipoUsuario existing = new TipoUsuario();
        existing.setId(2L);
        existing.setTipo("Velho");

        when(tipoUsuarioRepository.findById(2L)).thenReturn(Optional.of(existing));
        when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenAnswer(i -> i.getArgument(0));

        UpdateTipoUsuarioRecord rec = new UpdateTipoUsuarioRecord();
        rec.setTipo("Novo");

        var resp = tipoUsuarioService.atualizar(2L, rec);
        assertNotNull(resp);
        assertEquals("Novo", resp.getTipo());
    }

    @Test
    void atualizar_shouldThrow_whenNotFound() {
        when(tipoUsuarioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(InvalidTipoUsuarioException.class, () -> tipoUsuarioService.atualizar(99L, new UpdateTipoUsuarioRecord()));
    }

    @Test
    void deletar_shouldDelete_whenExists() {
        when(tipoUsuarioRepository.existsById(3L)).thenReturn(true);
        tipoUsuarioService.deletar(3L);
        verify(tipoUsuarioRepository).deleteById(3L);
    }

    @Test
    void deletar_shouldThrow_whenNotFound() {
        when(tipoUsuarioRepository.existsById(4L)).thenReturn(false);
        assertThrows(InvalidTipoUsuarioException.class, () -> tipoUsuarioService.deletar(4L));
    }

    @Test
    void acharTodosOsTipos_shouldThrow_onInvalidPageOrSize() {
        assertThrows(InvalidTipoUsuarioException.class, () -> tipoUsuarioService.acharTodosOsTipos(-1, 10));
        assertThrows(InvalidTipoUsuarioException.class, () -> tipoUsuarioService.acharTodosOsTipos(1, 0));
    }

    @Test
    void acharTodosOsTipos_shouldReturnList() {
        when(tipoUsuarioRepository.findAllTipoUsuario(10, 0)).thenReturn(List.of(new TipoUsuario()));
        var res = tipoUsuarioService.acharTodosOsTipos(1, 10);
        assertFalse(res.isEmpty());
    }
}

