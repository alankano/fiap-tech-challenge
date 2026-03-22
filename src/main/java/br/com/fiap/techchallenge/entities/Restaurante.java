package br.com.fiap.techchallenge.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurantes")
public class Restaurante implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID único do item", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do restaurante", example = "Restaurante Saboroso")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Schema(description = "Endereco do restaurante", example = "Rua das Flores, 123, São Paulo, SP")
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Schema(description = "Tipo de cozinha", example = "Japonesa")
    @Column(name = "tipo_cozinha", nullable = false)
    private String tipoCozinha;

    @JdbcTypeCode(SqlTypes.JSON)
    @Schema(description = "Dias de funcionamento", example = "[Segunda, Terça, Quarta, Quinta, Sexta]")
    @Column(name = "dias_funcionamento", nullable = false)
    private List<String> diasFuncionamento;

    @Schema(description = "Horário de abertura", example = "08:00")
    @Column(name = "horario_abertura", nullable = false)
    private String horarioAbertura;

    @Schema(description = "Horário de fechamento", example = "22:00")
    @Column(name = "horario_fechamento", nullable = false)
    private String horarioFechamento;

    @Schema(description = "id do dono Restaurante", example = "1")
    @Column(name = "id_usuario", nullable = false)
    private String idUsuario;

    // One restaurante pode ter vários itens. MappedBy referencia o campo 'restaurante' em Item.
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Item> itens = new java.util.ArrayList<>();
}
