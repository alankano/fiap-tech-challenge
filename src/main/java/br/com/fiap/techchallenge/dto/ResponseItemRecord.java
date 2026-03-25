package br.com.fiap.techchallenge.dto;

import br.com.fiap.techchallenge.entities.Item;
import br.com.fiap.techchallenge.entities.Restaurante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ResponseItemRecord {

    @NotNull
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal preco;

    @NotBlank
    private String disponibilidade;

    @NotBlank
    private String imagem;

    public ResponseItemRecord(Item item) {
        this.id = item.getId();
        this.nome = item.getNome();
        this.descricao = item.getDescricao();
        this.preco = item.getPreco();
        this.disponibilidade = item.getDisponibilidade();
        this.imagem = item.getImagem();
    }

}
