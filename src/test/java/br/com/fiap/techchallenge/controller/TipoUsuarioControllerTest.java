package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.dto.CreateTipoUsuarioRecord;
import br.com.fiap.techchallenge.entities.TipoUsuario;
import br.com.fiap.techchallenge.services.TipoUsuarioService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TipoUsuarioControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private TipoUsuarioService tipoUsuarioService;

    @InjectMocks
    private TipoUsuarioController tipoUsuarioController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tipoUsuarioController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findAllTipos_shouldReturnList() throws Exception {
        TipoUsuario t = new TipoUsuario(); t.setId(1L);
        when(tipoUsuarioService.acharTodosOsTipos(1,10)).thenReturn(List.of(t));

        mockMvc.perform(get("/tipo-usuario?page=1&size=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(tipoUsuarioService).acharTodosOsTipos(1,10);
    }

    @Test
    void saveTipoUsuario_shouldReturnCreated() throws Exception {
        CreateTipoUsuarioRecord dto = new CreateTipoUsuarioRecord(); dto.setTipo("Cliente");
        TipoUsuario saved = new TipoUsuario(); saved.setId(2L); saved.setTipo("Cliente");
        when(tipoUsuarioService.criar(any(TipoUsuario.class))).thenReturn(saved);

        mockMvc.perform(post("/tipo-usuario").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(tipoUsuarioService).criar(any(TipoUsuario.class));
    }
}
