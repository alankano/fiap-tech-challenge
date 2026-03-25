package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.entities.Usuario;
import br.com.fiap.techchallenge.services.UsuarioService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findAllUsuarios_shouldReturnList() throws Exception {
        Usuario u = new Usuario(); u.setId(1L);
        when(usuarioService.findAllUsuario(1,10)).thenReturn(List.of(u));

        mockMvc.perform(get("/usuarios?page=1&size=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(usuarioService).findAllUsuario(1,10);
    }

    @Test
    void findUsuarioById_shouldReturnOptional() throws Exception {
        Usuario u = new Usuario(); u.setId(2L);
        when(usuarioService.findByIdUsuario(2L)).thenReturn(Optional.of(u));

        mockMvc.perform(get("/usuarios/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));

        verify(usuarioService).findByIdUsuario(2L);
    }

    @Test
    void saveUsuario_shouldReturnCreated() throws Exception {
        Usuario u = new Usuario(); u.setNome("alan"); u.setEmail("alan@example.com");
        doNothing().when(usuarioService).saveUsuario(any(Usuario.class));

        mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(u)))
                .andExpect(status().isCreated());

        verify(usuarioService).saveUsuario(any(Usuario.class));
    }
}

