package br.com.fiap.techchallenge.services.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionsTest {

    @Test
    void badRequestException_hasMessage_andIsRuntime() {
        BadRequestException ex = new BadRequestException("bad request");
        assertEquals("bad request", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> { throw ex; });
        assertEquals("bad request", thrown.getMessage());
    }

    @Test
    void invalidLoginException_hasMessage_andIsRuntime() {
        InvalidLoginException ex = new InvalidLoginException("invalid login");
        assertEquals("invalid login", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
        InvalidLoginException thrown = assertThrows(InvalidLoginException.class, () -> { throw ex; });
        assertEquals("invalid login", thrown.getMessage());
    }

    @Test
    void invalidSenhaException_hasMessage_andIsRuntime() {
        InvalidSenhaException ex = new InvalidSenhaException("invalid senha");
        assertEquals("invalid senha", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
        InvalidSenhaException thrown = assertThrows(InvalidSenhaException.class, () -> { throw ex; });
        assertEquals("invalid senha", thrown.getMessage());
    }

    @Test
    void invalidUsuarioException_hasMessage_andIsRuntime() {
        InvalidUsuarioException ex = new InvalidUsuarioException("invalid usuario");
        assertEquals("invalid usuario", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
        InvalidUsuarioException thrown = assertThrows(InvalidUsuarioException.class, () -> { throw ex; });
        assertEquals("invalid usuario", thrown.getMessage());
    }

    @Test
    void resourceNotFoundException_hasMessage_andIsRuntime() {
        ResourceNotFoundException ex = new ResourceNotFoundException("not found");
        assertEquals("not found", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> { throw ex; });
        assertEquals("not found", thrown.getMessage());
    }
}

