package br.com.fiap.techchallenge.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurantes")
public class Restaurantes implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID único do item", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do restaurante", example = "Restaurante Saboroso")
    @Column(name = "nome")
    private String nome;

    @Schema(description = "Endereco do restaurante", example = "Rua das Flores, 123, São Paulo, SP")
    @Column(name = "endereco")
    private String endereco;

    @Schema(description = "Tipo de cozinha", example = "Japonesa")
    @Column(name = "tipo_cozinha")
    private String tipoCozinha;

    @Schema(description = "Dias de funcionamento", example = "Segunda, Terça, Quarta, Quinta, Sexta")
    @Column(name = "dias_funcionamento")
    private List<String> diasFuncionamento;

    @Schema(description = "Horário de abertura", example = "08:00")
    @Column(name = "horario_abertura")
    private String horarioAbertura;

    @Schema(description = "Horário de fechamento", example = "22:00")
    @Column(name = "horario_fechamento")
    private String horarioFechamento;

    @Schema(description = "id do dono Restaurante", example = "1")
    @Column(name = "id_usuario", nullable = false)
    private String idUsuario;

}
