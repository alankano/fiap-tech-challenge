package br.com.fiap.techchallenge.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationErrorDtoTest {

    @Test
    void constructorAndJson() throws Exception {
        ValidationErrorDto dto = new ValidationErrorDto(List.of("err1","err2"), 400);
        assertEquals(2, dto.getErrors().size());
        assertEquals(400, dto.getStatus());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);
        assertTrue(json.contains("err1"));

        ValidationErrorDto read = mapper.readValue(json, ValidationErrorDto.class);
        assertEquals(dto.getErrors().size(), read.getErrors().size());
    }
}

