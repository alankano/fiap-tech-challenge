package br.com.fiap.techchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionDto {


    private String message;
    private int status;

    public ExceptionDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
