package br.com.fiap.techchallenge.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateTipoUsuarioRecordTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void close() {
        validatorFactory.close();
    }

    @Test
    void valid_shouldPass() {
        CreateTipoUsuarioRecord dto = new CreateTipoUsuarioRecord();
        dto.setTipo("Cliente");
        var violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void blankTipo_shouldFail() {
        CreateTipoUsuarioRecord dto = new CreateTipoUsuarioRecord();
        dto.setTipo("");
        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("tipo")));
    }
}

