package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.dto.CreateItemRecord;
import br.com.fiap.techchallenge.dto.UpdateItemRecord;
import br.com.fiap.techchallenge.entities.Item;
import br.com.fiap.techchallenge.services.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ItemControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findItensByRestaurante_shouldReturnList() throws Exception {
        Item i = new Item();
        i.setId(1L);
        i.setNome("Pave");
        when(itemService.acharItensPorRestaurante(1L, 1, 10)).thenReturn(List.of(i));

        mockMvc.perform(get("/itens/restaurantes/1?page=1&size=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(itemService).acharItensPorRestaurante(1L, 1, 10);
    }

    @Test
    void saveItem_shouldReturnCreated() throws Exception {
        CreateItemRecord dto = new CreateItemRecord();
        dto.setNome("Pave");
        dto.setDescricao("Delicious");
        dto.setPreco(BigDecimal.valueOf(10));
        dto.setDisponibilidade("Sim");
        dto.setImagem("/img.png");

        Item saved = new Item();
        saved.setId(2L);
        saved.setNome(dto.getNome());

        when(itemService.criar(any(Item.class), eq(1L))).thenReturn(saved);

        mockMvc.perform(post("/itens?restauranteId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Pave"));

        verify(itemService).criar(any(Item.class), eq(1L));
    }

    @Test
    void updateItem_shouldReturnNoContent() throws Exception {
        UpdateItemRecord dto = new UpdateItemRecord();
        dto.setNome("Updated");
        dto.setDescricao("D");
        dto.setPreco(BigDecimal.valueOf(5));
        dto.setDisponibilidade("Sim");
        dto.setImagem("/i.png");


        Item saved = new Item();
        saved.setId(1L);
        saved.setNome(dto.getNome());
        var response = new br.com.fiap.techchallenge.dto.ResponseItemRecord(saved);
        when(itemService.atualizar(eq(1L), any(UpdateItemRecord.class))).thenReturn(response);

        mockMvc.perform(put("/itens/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        verify(itemService).atualizar(eq(1L), any(UpdateItemRecord.class));
    }

    @Test
    void deleteItem_shouldReturnNoContent() throws Exception {
        doNothing().when(itemService).deletar(1L);

        mockMvc.perform(delete("/itens/1"))
                .andExpect(status().isNoContent());

        verify(itemService).deletar(1L);
    }
}
