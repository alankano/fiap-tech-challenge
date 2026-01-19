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
public class Usuario {

    @Schema(description = "ID único do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome do usuário", example = "alan")
    private String nome;

    @Schema(description = "Email do usuário", example = "alan.kano@gmail.com")
    private String email;

    @Schema(description = "Login do usuário", example = "alankano")
    private String login;

    @Schema(description = "Senha do usuário", example = "123")
    private String senha;

    @Schema(description = "Data da ultima alteracao do usuário", example = "2026-01-16")
    private LocalDate dataUltimaAlteracao;

    @Schema(description = "Endereco do usuário", example = "São Paulo, SP, Brasil")
    private String endereco;

    @Schema(description = "Tipo do usuário", example = "1")
    private String tipoUsuario;

}
