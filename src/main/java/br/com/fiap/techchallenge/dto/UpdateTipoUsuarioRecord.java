package br.com.fiap.techchallenge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTipoUsuarioRecord {

    @NotBlank
    private String tipo;

}
