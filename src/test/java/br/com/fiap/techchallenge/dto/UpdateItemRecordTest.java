package br.com.fiap.techchallenge.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UpdateItemRecordTest {

    @Test
    void gettersAndSettersAndJson() throws Exception {
        UpdateItemRecord u = new UpdateItemRecord();
        u.setNome("N");
        u.setDescricao("D");
        u.setPreco(BigDecimal.valueOf(12.3));
        u.setDisponibilidade("Sim");
        u.setImagem("/i.png");

        assertEquals("N", u.getNome());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(u);
        assertTrue(json.contains("N"));

        UpdateItemRecord read = mapper.readValue(json, UpdateItemRecord.class);
        assertEquals(u.getNome(), read.getNome());
    }
}

