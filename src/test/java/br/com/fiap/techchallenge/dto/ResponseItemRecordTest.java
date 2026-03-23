package br.com.fiap.techchallenge.dto;

import br.com.fiap.techchallenge.entities.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ResponseItemRecordTest {

    @Test
    void constructorAndAccessors() {
        Item i = new Item();
        i.setId(1L);
        i.setNome("X");
        i.setDescricao("D");
        i.setPreco(BigDecimal.valueOf(10));
        i.setDisponibilidade("Sim");
        i.setImagem("/img.png");

        ResponseItemRecord r = new ResponseItemRecord(i);
        assertEquals(1L, r.getId());
        assertEquals("X", r.getNome());
        assertEquals("D", r.getDescricao());
    }

}

