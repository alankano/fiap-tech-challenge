package br.com.fiap.techchallenge.dto;

import br.com.fiap.techchallenge.entities.Restaurante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseRestauranteRecord {

    @NotNull
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotBlank
    private String tipoCozinha;

    @Size(min = 1)
    private List<String> diasFuncionamento;

    @NotBlank
    private String horarioAbertura;

    @NotBlank
    private String horarioFechamento;

    @NotBlank
    private String idUsuario;

    public ResponseRestauranteRecord(Restaurante restaurante){
        this.id = restaurante.getId();
        this.nome = restaurante.getNome();
        this.endereco = restaurante.getEndereco();
        this.tipoCozinha = restaurante.getTipoCozinha();
        this.diasFuncionamento = restaurante.getDiasFuncionamento();
        this.horarioAbertura = restaurante.getHorarioAbertura();
        this.horarioFechamento = restaurante.getHorarioFechamento();
        this.idUsuario = restaurante.getIdUsuario();
    }

}
