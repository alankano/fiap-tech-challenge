package br.com.fiap.techchallenge.dto;

import br.com.fiap.techchallenge.entities.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseTipoUsuarioRecord {

    @NotBlank
    private String tipo;

    public ResponseTipoUsuarioRecord(TipoUsuario tipoUsuario) {
        this.tipo = tipoUsuario.getTipo();
    }

}
