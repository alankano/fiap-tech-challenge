package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.dto.UpdateItemRecord;
import br.com.fiap.techchallenge.entities.Item;
import br.com.fiap.techchallenge.entities.Restaurante;
import br.com.fiap.techchallenge.repositories.ItemRepository;
import br.com.fiap.techchallenge.repositories.RestaurantesRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private RestaurantesRepository restaurantesRepository;

    @InjectMocks
    private ItemService itemService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void acharItensPorRestaurante_shouldThrow_whenRestauranteNotExists() {
        when(itemRepository.existsRestauranteById(1L)).thenReturn(false);

        assertThrows(InvalidItemException.class, () -> itemService.acharItensPorRestaurante(1L,1,10));
    }

    @Test
    void criar_shouldAssociateRestaurante_andSave() {
        Item i = new Item(); i.setNome("X");
        when(itemRepository.existsByNomeAndRestauranteId("X", 1L)).thenReturn(false);
        when(itemRepository.existsRestauranteById(1L)).thenReturn(true);
        when(restaurantesRepository.findById(1L)).thenReturn(Optional.of(new Restaurante()));
        when(itemRepository.save(any(Item.class))).thenAnswer(inv -> { Item it = inv.getArgument(0); it.setId(5L); return it;});

        var saved = itemService.criar(i, 1L);
        assertEquals(5L, saved.getId());
    }

    @Test
    void atualizar_shouldReturnResponse_whenExists() {
        Item item = new Item(); item.setId(2L); item.setNome("Old");
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));

        UpdateItemRecord dto = new UpdateItemRecord(); dto.setNome("New"); dto.setDescricao("D"); dto.setPreco(BigDecimal.valueOf(1)); dto.setDisponibilidade("Sim"); dto.setImagem("/i.png");

        var res = itemService.atualizar(2L, dto);
        assertEquals("New", res.getNome());
    }

    @Test
    void acharItensPorRestaurante_shouldReturnList_whenExists() {
        Item i = new Item(); i.setId(1L); i.setNome("X");
        when(itemRepository.existsRestauranteById(1L)).thenReturn(true);
        when(itemRepository.findByRestauranteId(1L, 10, 0)).thenReturn(Optional.of(List.of(i)));

        var res = itemService.acharItensPorRestaurante(1L,1,10);
        assertFalse(res.isEmpty());
    }

    @Test
    void deletar_shouldThrow_whenNotFound() {
        when(itemRepository.existsById(9L)).thenReturn(false);
        assertThrows(br.com.fiap.techchallenge.services.exceptions.InvalidItemException.class, () -> itemService.deletar(9L));
    }

    @Test
    void criar_shouldThrow_whenNameExistsInRestaurante() {
        Item i = new Item(); i.setNome("X");
        when(itemRepository.existsByNomeAndRestauranteId("X", 1L)).thenReturn(true);
        assertThrows(InvalidItemException.class, () -> itemService.criar(i, 1L));
    }

    @Test
    void deletar_shouldDelete_whenExists() {
        when(itemRepository.existsById(8L)).thenReturn(true);
        doNothing().when(itemRepository).deleteById(8L);
        assertDoesNotThrow(() -> itemService.deletar(8L));
        verify(itemRepository).deleteById(8L);
    }

    @Test
    void acharItensPorRestaurante_shouldThrow_whenInvalidPageOrSize() {
        when(itemRepository.existsRestauranteById(1L)).thenReturn(true);
        assertThrows(InvalidItemException.class, () -> itemService.acharItensPorRestaurante(1L, -1, 10));
        assertThrows(InvalidItemException.class, () -> itemService.acharItensPorRestaurante(1L, 1, 0));
    }

    @Test
    void findByNomeItem_shouldThrow_whenNotFound() {
        when(itemRepository.findByNome("Nope")).thenReturn(Optional.empty());
        assertThrows(InvalidItemException.class, () -> itemService.findByNomeItem("Nope"));
    }

    @Test
    void criar_shouldThrow_whenRestauranteIdNullOrMissing() {
        Item i = new Item(); i.setNome("Y");
        // null restauranteId
        assertThrows(InvalidItemException.class, () -> itemService.criar(i, null));

        // restaurante id provided but not exists
        when(itemRepository.existsByNomeAndRestauranteId("Y", 2L)).thenReturn(false);
        when(itemRepository.existsRestauranteById(2L)).thenReturn(false);
        assertThrows(InvalidItemException.class, () -> itemService.criar(i, 2L));
    }

    @Test
    void atualizar_shouldThrow_whenNotFound() {
        when(itemRepository.findById(99L)).thenReturn(Optional.empty());
        UpdateItemRecord dto = new UpdateItemRecord(); dto.setNome("N");
        assertThrows(br.com.fiap.techchallenge.services.exceptions.InvalidItemException.class, () -> itemService.atualizar(99L, dto));
    }

    @Test
    void findByNomeItem_shouldReturnItem_whenExists() {
        Item i = new Item(); i.setId(11L); i.setNome("Exist");
        when(itemRepository.findByNome("Exist")).thenReturn(Optional.of(i));

        var res = itemService.findByNomeItem("Exist");
        assertNotNull(res);
        assertEquals(11L, res.getId());
    }

    @Test
    void criar_shouldAssociateRestaurante_whenRestaurantePresent() {
        Item i = new Item(); i.setNome("Z");
        Restaurante r = new Restaurante(); r.setId(7L);
        when(itemRepository.existsByNomeAndRestauranteId("Z", 7L)).thenReturn(false);
        when(itemRepository.existsRestauranteById(7L)).thenReturn(true);
        when(restaurantesRepository.findById(7L)).thenReturn(Optional.of(r));
        when(itemRepository.save(any(Item.class))).thenAnswer(inv -> inv.getArgument(0));

        var saved = itemService.criar(i, 7L);
        assertNotNull(saved.getRestaurante());
        assertEquals(7L, saved.getRestaurante().getId());
    }

    @Test
    void atualizar_responseContainsFields() {
        Item item = new Item(); item.setId(20L); item.setNome("Old"); item.setDescricao("old desc");
        when(itemRepository.findById(20L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));

        UpdateItemRecord dto = new UpdateItemRecord();
        dto.setNome("NewName");
        dto.setDescricao("NewDesc");
        dto.setDisponibilidade("No");
        dto.setPreco(new java.math.BigDecimal("12.50"));
        dto.setImagem("/img2.png");

        var res = itemService.atualizar(20L, dto);
        assertEquals("NewName", res.getNome());
        assertEquals("NewDesc", res.getDescricao());
        assertEquals("No", res.getDisponibilidade());
    }

}
