package br.com.fiap.techchallenge.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @Schema(description = "Login do usuário", example = "alankano")
    private String login;

    @Schema(description = "Senha do usuário", example = "123")
    private String senha;

}
