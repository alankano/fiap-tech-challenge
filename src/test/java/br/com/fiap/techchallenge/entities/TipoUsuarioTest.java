package br.com.fiap.techchallenge.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoUsuarioTest {

    @Test
    void gettersAndSetters() {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(1L);
        tipoUsuario.setTipo("tipo");


        assertEquals(1L, tipoUsuario.getId());
        assertEquals("tipo", tipoUsuario.getTipo());

    }

}
