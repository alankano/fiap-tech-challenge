package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.entities.Senha;
import br.com.fiap.techchallenge.services.SenhaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SenhaControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private SenhaService senhaService;

    @InjectMocks
    private SenhaController senhaController;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(senhaController).build();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) mocks.close();
    }

    @Test
    void updateSenha_shouldReturnNoContent() throws Exception {
        Senha s = new Senha();
        s.setSenha("old");
        s.setNovaSenha("new");
        s.setNovaSenha2("new");

        doNothing().when(senhaService).updateSenha(any(Senha.class), eq(1L));

        mockMvc.perform(put("/senha/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isNoContent());

        verify(senhaService).updateSenha(any(Senha.class), eq(1L));
    }
}
