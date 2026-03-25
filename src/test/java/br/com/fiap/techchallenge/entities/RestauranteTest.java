package br.com.fiap.techchallenge.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    @Test
    void gettersAndSetters() {
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNome("restaurante");
        restaurante.setEndereco("endereco");
        restaurante.setTipoCozinha("tipoCozinha");
        restaurante.setHorarioAbertura("08:00");
        restaurante.setHorarioFechamento("22:00");
        restaurante.setIdUsuario("1");

        assertEquals(1L, restaurante.getId());
        assertEquals("restaurante", restaurante.getNome());
        assertEquals("endereco", restaurante.getEndereco());
        assertEquals("tipoCozinha", restaurante.getTipoCozinha());
        assertEquals("08:00", restaurante.getHorarioAbertura());
        assertEquals("22:00", restaurante.getHorarioFechamento());
        assertEquals("1", restaurante.getIdUsuario());
    }
}

