package br.com.fiap.techchallenge.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionDtoTest {

    @Test
    void constructorAndAccessorsWork() {
        ExceptionDto dto = new ExceptionDto("erro", 400);
        assertEquals("erro", dto.getMessage());
        assertEquals(400, dto.getStatus());

        dto.setMessage("novo");
        dto.setStatus(401);
        assertEquals("novo", dto.getMessage());
        assertEquals(401, dto.getStatus());
    }

    @Test
    void jsonSerializationAndDeserialization() throws Exception {
        ExceptionDto dto = new ExceptionDto("not found", 404);
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(dto);
        assertTrue(json.contains("not found"));
        assertTrue(json.contains("404"));

        ExceptionDto read = mapper.readValue(json, ExceptionDto.class);
        assertNotNull(read);
        assertEquals(dto.getMessage(), read.getMessage());
        assertEquals(dto.getStatus(), read.getStatus());
    }
}

