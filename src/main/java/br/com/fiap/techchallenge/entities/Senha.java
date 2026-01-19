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
public class Senha {

    @Schema(description = "Id do usuário", example = "1")
    private Long id;

    @Schema(description = "Data da ultima alteracao do usuário", example = "2026-01-16")
    private LocalDate dataUltimaAlteracao;

    @Schema(description = "Senha do usuário", example = "123")
    private String senha;

    @Schema(description = "Nova senha do usuario 1", example = "321")
    private String novaSenha;

    @Schema(description = "Nova senha do usuario 2", example = "321")
    private String novaSenha2;

}
