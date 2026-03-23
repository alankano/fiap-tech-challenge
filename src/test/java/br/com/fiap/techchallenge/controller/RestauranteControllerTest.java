package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.dto.CreateRestauranteRecord;
import br.com.fiap.techchallenge.dto.UpdateRestauranteRecord;
import br.com.fiap.techchallenge.entities.Restaurante;
import br.com.fiap.techchallenge.services.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RestauranteControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private RestauranteService restauranteService;

    @InjectMocks
    private RestauranteController restauranteController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findAllRestaurante_shouldReturnList() throws Exception {
        Restaurante r = new Restaurante(); r.setId(1L);
        when(restauranteService.findAllRestaurantes(1,10)).thenReturn(List.of(r));

        mockMvc.perform(get("/restaurantes?page=1&size=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(restauranteService).findAllRestaurantes(1,10);
    }

    @Test
    void saveRestaurante_shouldReturnCreated() throws Exception {
        CreateRestauranteRecord dto = new CreateRestauranteRecord();
        dto.setNome("R"); dto.setEndereco("E"); dto.setTipoCozinha("T"); dto.setDiasFuncionamento(List.of("Seg")); dto.setHorarioAbertura("08:00"); dto.setHorarioFechamento("22:00"); dto.setIdUsuario("1");

        var response = new br.com.fiap.techchallenge.dto.ResponseRestauranteRecord(new Restaurante());
        when(restauranteService.criar(any(CreateRestauranteRecord.class))).thenReturn(response);

        mockMvc.perform(post("/restaurantes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(restauranteService).criar(any(CreateRestauranteRecord.class));
    }

    @Test
    void updateRestaurante_shouldReturnNoContent() throws Exception {
        UpdateRestauranteRecord dto = new UpdateRestauranteRecord(); dto.setNome("R"); dto.setEndereco("E"); dto.setTipoCozinha("T"); dto.setIdUsuario("1");
        // fill required fields validated by controller (@NotBlank)
        dto.setHorarioAbertura("08:00");
        dto.setHorarioFechamento("22:00");
        var response = new br.com.fiap.techchallenge.dto.ResponseRestauranteRecord(new Restaurante());
        when(restauranteService.atualizar(eq(1L), any(UpdateRestauranteRecord.class))).thenReturn(response);

        mockMvc.perform(put("/restaurantes/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        verify(restauranteService).atualizar(eq(1L), any(UpdateRestauranteRecord.class));
    }
}
