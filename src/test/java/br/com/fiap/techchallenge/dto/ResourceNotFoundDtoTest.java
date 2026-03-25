package br.com.fiap.techchallenge.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundDtoTest {

    @Test
    void recordConstructorAndAccessors() {
        ResourceNotFoundDto dto = new ResourceNotFoundDto("Resource not found", 404);
        assertEquals("Resource not found", dto.message());
        assertEquals(404, dto.status());
    }

    @Test
    void jsonSerializationAndDeserialization() throws Exception {
        ResourceNotFoundDto dto = new ResourceNotFoundDto("Item not found", 404);
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(dto);
        assertTrue(json.contains("Item not found"));
        assertTrue(json.contains("404"));

        ResourceNotFoundDto read = mapper.readValue(json, ResourceNotFoundDto.class);
        assertNotNull(read);
        assertEquals(dto.message(), read.message());
        assertEquals(dto.status(), read.status());
    }

    @Test
    void equalsAndHashCode() {
        ResourceNotFoundDto dto1 = new ResourceNotFoundDto("Not found", 404);
        ResourceNotFoundDto dto2 = new ResourceNotFoundDto("Not found", 404);
        ResourceNotFoundDto dto3 = new ResourceNotFoundDto("Different", 404);

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}

