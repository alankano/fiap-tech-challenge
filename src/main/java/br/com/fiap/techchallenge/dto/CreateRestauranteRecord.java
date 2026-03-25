package br.com.fiap.techchallenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateRestauranteRecord {

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


}
