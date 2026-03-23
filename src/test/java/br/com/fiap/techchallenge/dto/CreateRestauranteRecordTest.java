package br.com.fiap.techchallenge.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateRestauranteRecordTest {

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
    void validCreateRestauranteRecord_shouldHaveNoViolations() {
        CreateRestauranteRecord dto = new CreateRestauranteRecord();
        dto.setNome("R Teste");
        dto.setEndereco("Endereco");
        dto.setTipoCozinha("Italiana");
        dto.setDiasFuncionamento(List.of("Segunda","Terca"));
        dto.setHorarioAbertura("08:00");
        dto.setHorarioFechamento("22:00");
        dto.setIdUsuario("1");

        var violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "DTO válido não deve ter violações");
    }

    @Test
    void invalidWhenNomeBlank_shouldHaveViolation() {
        CreateRestauranteRecord dto = new CreateRestauranteRecord();
        dto.setNome("");
        dto.setEndereco("E");
        dto.setTipoCozinha("T");
        dto.setDiasFuncionamento(List.of("Segunda"));
        dto.setHorarioAbertura("08:00");
        dto.setHorarioFechamento("22:00");
        dto.setIdUsuario("1");

        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    void invalidWhenDiaElementBlank_shouldHaveViolation() {
        CreateRestauranteRecord dto = new CreateRestauranteRecord();
        dto.setNome("");
        dto.setEndereco("E");
        dto.setTipoCozinha("T");
        dto.setDiasFuncionamento(List.of( "Segunda"));
        dto.setHorarioAbertura("08:00");
        dto.setHorarioFechamento("22:00");
        dto.setIdUsuario("1");

        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().contains("nome")));
    }
}
