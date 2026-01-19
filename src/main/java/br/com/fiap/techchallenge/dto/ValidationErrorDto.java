package br.com.fiap.techchallenge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ValidationErrorDto {

    private List<String> errors;
    private int status;

    public ValidationErrorDto(List<String> errors, int status) {
        this.errors = errors;
        this.status = status;
    }

}
