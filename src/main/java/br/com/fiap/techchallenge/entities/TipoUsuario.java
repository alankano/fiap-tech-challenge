package br.com.fiap.techchallenge.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "tipo_usuario")
public class TipoUsuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID único do tipo usuário", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Tipo de usuário", example = "Cliente")
    @Column(name = "tipo")
    private String tipo;

}
