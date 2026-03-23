package br.com.fiap.techchallenge.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateTipoUsuarioRecordTest {

    @Test
    void gettersAndJson() throws Exception {
        UpdateTipoUsuarioRecord u = new UpdateTipoUsuarioRecord();
        u.setTipo("Admin");
        assertEquals("Admin", u.getTipo());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(u);
        UpdateTipoUsuarioRecord read = mapper.readValue(json, UpdateTipoUsuarioRecord.class);
        assertEquals(u.getTipo(), read.getTipo());
    }
}

