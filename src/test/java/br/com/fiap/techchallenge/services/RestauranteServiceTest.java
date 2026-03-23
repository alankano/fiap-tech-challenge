package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.dto.CreateRestauranteRecord;
import br.com.fiap.techchallenge.dto.UpdateRestauranteRecord;
import br.com.fiap.techchallenge.entities.Restaurante;
import br.com.fiap.techchallenge.repositories.RestaurantesRepository;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidRestauranteException;
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

class RestauranteServiceTest {

    @Mock
    private RestaurantesRepository restaurantesRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private RestauranteService restauranteService;

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
    void criar_shouldThrow_whenNomeExists() {
        CreateRestauranteRecord dto = new CreateRestauranteRecord();
        dto.setNome("R"); dto.setIdUsuario("1");
        when(restaurantesRepository.existsByNome("R")).thenReturn(true);

        assertThrows(InvalidRestauranteException.class, () -> restauranteService.criar(dto));
    }

    @Test
    void criar_shouldThrow_whenUsuarioNotFound() {
        CreateRestauranteRecord dto = new CreateRestauranteRecord();
        dto.setNome("R"); dto.setIdUsuario("9");
        when(restaurantesRepository.existsByNome("R")).thenReturn(false);
        when(usuarioRepository.findUsuarioById(9L)).thenReturn(Optional.empty());

        assertThrows(InvalidRestauranteException.class, () -> restauranteService.criar(dto));
    }

    @Test
    void criar_shouldSave_whenValid() {
        CreateRestauranteRecord dto = new CreateRestauranteRecord();
        dto.setNome("Novo"); dto.setEndereco("E"); dto.setTipoCozinha("T"); dto.setDiasFuncionamento(List.of("Seg")); dto.setHorarioAbertura("08:00"); dto.setHorarioFechamento("22:00"); dto.setIdUsuario("2");

        when(restaurantesRepository.existsByNome("Novo")).thenReturn(false);
        when(usuarioRepository.findUsuarioById(2L)).thenReturn(Optional.of(new br.com.fiap.techchallenge.entities.Usuario()));
        when(restaurantesRepository.save(any(Restaurante.class))).thenAnswer(inv -> {
            Restaurante r = inv.getArgument(0);
            r.setId(10L);
            return r;
        });

        var resp = restauranteService.criar(dto);
        assertNotNull(resp);
        assertEquals(10L, resp.getId());
    }

    @Test
    void findAllRestaurantes_shouldCallRepository() {
        when(restaurantesRepository.findAllRestaurante(10, 0)).thenReturn(List.of(new Restaurante()));
        var res = restauranteService.findAllRestaurantes(1,10);
        assertFalse(res.isEmpty());
    }

    @Test
    void atualizar_shouldThrow_whenUsuarioInvalid() {
        UpdateRestauranteRecord dto = new UpdateRestauranteRecord(); dto.setIdUsuario("x");
        when(restaurantesRepository.findById(1L)).thenReturn(Optional.of(new Restaurante()));
        assertThrows(InvalidRestauranteException.class, () -> restauranteService.atualizar(1L, dto));
    }

    @Test
    void atualizar_shouldUpdate_whenValid() {
        UpdateRestauranteRecord dto = new UpdateRestauranteRecord();
        dto.setNome("R2"); dto.setEndereco("E2"); dto.setTipoCozinha("T2"); dto.setDiasFuncionamento(List.of("Seg")); dto.setHorarioAbertura("08:00"); dto.setHorarioFechamento("22:00"); dto.setIdUsuario("3");

        Restaurante existing = new Restaurante(); existing.setId(1L);
        when(restaurantesRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(usuarioRepository.findUsuarioById(3L)).thenReturn(Optional.of(new br.com.fiap.techchallenge.entities.Usuario()));
        when(restaurantesRepository.save(any(Restaurante.class))).thenAnswer(i -> i.getArgument(0));

        var resp = restauranteService.atualizar(1L, dto);
        assertEquals("R2", resp.getNome());
    }

    @Test
    void deletar_shouldDelete_whenExists() {
        when(restaurantesRepository.existsById(5L)).thenReturn(true);
        restauranteService.deletar(5L);
        verify(restaurantesRepository).deleteById(5L);
    }

    @Test
    void deletar_shouldThrow_whenNotFound() {
        when(restaurantesRepository.existsById(6L)).thenReturn(false);
        assertThrows(InvalidRestauranteException.class, () -> restauranteService.deletar(6L));
    }

}
