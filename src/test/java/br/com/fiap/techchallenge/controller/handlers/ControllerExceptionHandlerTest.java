package br.com.fiap.techchallenge.controller.handlers;

import br.com.fiap.techchallenge.dto.ExceptionDto;
import br.com.fiap.techchallenge.dto.ResourceNotFoundDto;
import br.com.fiap.techchallenge.dto.ValidationErrorDto;
import br.com.fiap.techchallenge.services.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerExceptionHandlerTest {

    private final ControllerExceptionHandler handler = new ControllerExceptionHandler();

    @Test
    void handlerResourceNotFoundException_returns404_andBody() {
        ResourceNotFoundException ex = new ResourceNotFoundException("rest not found");
        var resp = handler.handlerResourceNotFoundException(ex);

        assertEquals(404, resp.getStatusCode().value());
        ResourceNotFoundDto body = resp.getBody();
        assertNotNull(body);
        assertEquals("rest not found", body.message());
        assertEquals(404, body.status());
    }

    @Test
    void handlerInvalidLoginException_returns400_andExceptionDto() {
        InvalidLoginException ex = new InvalidLoginException("bad login");
        var resp = handler.handlerInvalidLoginException(ex);

        assertEquals(400, resp.getStatusCode().value());
        ExceptionDto body = resp.getBody();
        assertNotNull(body);
        assertEquals("bad login", body.getMessage());
        assertEquals(400, body.getStatus());
    }

    @Test
    void handlerBadRequestException_forBadRequest_returns400() {
        BadRequestException ex = new BadRequestException("invalid input");
        var resp = handler.handlerBadRequestException(ex);

        assertEquals(400, resp.getStatusCode().value());
        ExceptionDto body = resp.getBody();
        assertNotNull(body);
        assertEquals("invalid input", body.getMessage());
        assertEquals(400, body.getStatus());
    }

    @Test
    void handlerMethodArgumentNotValidException_buildsValidationErrors() {
        // mock BindingResult to return a FieldError list
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fe = new FieldError("obj","nome","não deve estar em branco");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fe));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        var resp = handler.handlerMethodArgumentNotValidException(ex);
        assertEquals(400, resp.getStatusCode().value());
        ValidationErrorDto body = resp.getBody();
        assertNotNull(body);
        assertEquals(400, body.getStatus());
        assertNotNull(body.getErrors());
        assertTrue(body.getErrors().stream().anyMatch(s -> s.contains("nome: não deve estar em branco")));
    }

    @Test
    void handlerInvalidItemException_returns400_andMessage() {
        InvalidItemException ex = new InvalidItemException("item invalid");
        var resp = handler.handlerBadRequestException(ex);

        assertEquals(400, resp.getStatusCode().value());
        ExceptionDto body = resp.getBody();
        assertNotNull(body);
        assertEquals("item invalid", body.getMessage());
        assertEquals(400, body.getStatus());
    }

}
