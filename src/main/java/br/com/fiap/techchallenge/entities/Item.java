package br.com.fiap.techchallenge.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "itens")
public class Item implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID único do item", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do item", example = "Frango a Parmegiana")
    @Column(name = "nome")
    private String nome;

    @Schema(description = "Descricao do item", example = "Frango coberto com molho de tomate e queijo, acompanhado de arroz e batata frita")
    @Column(name = "descricao")
    private String descricao;

    @Schema(description = "Preco do item", example = "81,90")
    @Column(name = "preco", precision=10, scale = 2)
    private BigDecimal preco;

    @Schema(description = "Disponibilidade para pedir apenas no restaurante", example = "Sim")
    @Column(name = "disponibilidade")
    private String disponibilidade;

    @Schema(description = "Foto do prato", example = "/caminho/da/foto.jpg")
    @Column(name = "imagem", precision=10, scale = 2)
    private String imagem;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    @JsonBackReference
    private Restaurante restaurante;
}
