package br.com.fiap.techchallenge.controller.handlers;

import br.com.fiap.techchallenge.dto.ExceptionDto;
import br.com.fiap.techchallenge.dto.ResourceNotFoundDto;
import br.com.fiap.techchallenge.dto.ValidationErrorDto;
import br.com.fiap.techchallenge.services.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundDto> handlerResourceNotFoundException(ResourceNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status.value()).body(new ResourceNotFoundDto(ex.getMessage(), status.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDto> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        var status = HttpStatus.BAD_REQUEST;
        List<String> errors = new ArrayList<String>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return ResponseEntity.status(status.value()).body(new ValidationErrorDto(errors, status.value()));
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ExceptionDto> handlerInvalidLoginException(InvalidLoginException ex) {

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status.value()).body(new ExceptionDto(ex.getMessage(), status.value()));
    }

    @ExceptionHandler(InvalidSenhaException.class)
    public ResponseEntity<ExceptionDto> handlerInvalidSenhaException(InvalidSenhaException ex) {

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status.value()).body(new ExceptionDto(ex.getMessage(), status.value()));
    }

    @ExceptionHandler(InvalidUsuarioException.class)
    public ResponseEntity<ExceptionDto> handlerInvalidUsuarioException(InvalidUsuarioException ex) {

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status.value()).body(new ExceptionDto(ex.getMessage(), status.value()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> handlerBadRequestException(BadRequestException ex) {

        logger.warn("Bad request: {}", ex.getMessage());
        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status.value()).body(new ExceptionDto(ex.getMessage(), status.value()));
    }

}
