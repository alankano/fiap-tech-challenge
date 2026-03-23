package br.com.fiap.techchallenge.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreateItemRecordTest {

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
    void validCreateItemRecord_shouldPass() {
        CreateItemRecord dto = new CreateItemRecord();
        dto.setNome("ItemX");
        dto.setDescricao("Desc");
        dto.setPreco(BigDecimal.valueOf(12.5));
        dto.setDisponibilidade("Sim");
        dto.setImagem("/i.png");

        var v = validator.validate(dto);
        assertTrue(v.isEmpty());
    }

    @Test
    void missingFields_shouldFail() {
        CreateItemRecord dto = new CreateItemRecord();
        dto.setNome("");
        dto.setDescricao("");

        var v = validator.validate(dto);
        assertFalse(v.isEmpty());
    }
}

