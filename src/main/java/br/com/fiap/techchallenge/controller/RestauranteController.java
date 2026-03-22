package br.com.fiap.techchallenge.controller;


import br.com.fiap.techchallenge.dto.CreateRestauranteRecord;
import br.com.fiap.techchallenge.dto.ResponseRestauranteRecord;
import br.com.fiap.techchallenge.dto.UpdateRestauranteRecord;
import br.com.fiap.techchallenge.entities.Restaurante;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.fiap.techchallenge.services.RestauranteService;
import br.com.fiap.techchallenge.services.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "API de gerenciamento de restaurantes")
public class RestauranteController {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteController.class);

    @Autowired
    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Operation(description = "Buscar todos os restaurantes com paginação",
            summary = "Busca todos os restaurantes",
            parameters = {
                    @Parameter(name = "page", description = "Número da página", example = "0"),
                    @Parameter(name = "size", description = "Quantidade de itens por página", example = "10")
            },
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @GetMapping
    public ResponseEntity<List<Restaurante>> findAllRestaurante(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {

        if (page == null || size == null) {
            throw new BadRequestException("Parâmetros 'page' e 'size' são obrigatórios");
        }
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Parâmetros inválidos: 'page' deve ser >= 0 e 'size' > 0");
        }

        logger.info("Buscando restaurantes - Página: {}, Tamanho: {}", page, size);
        var restaurantes = restauranteService.findAllRestaurantes(page, size);
        return ResponseEntity.ok(restaurantes);
    }

    @Operation(description = "Buscar restaurantes pelo nome",
            summary = "Busca pelo nome",
            parameters = {
                    @Parameter(name = "nome", description = "Nome do restaurante", example = "Restaurante Saboroso")
            },
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")
            }
    )
    @GetMapping(path = "/buscaNome")
    public ResponseEntity<Restaurante> findRestauranteByNome(@RequestParam("nome") String nome) {

        if (nome == null || nome.isBlank()) {
            throw new BadRequestException("Parâmetro 'nome' é obrigatório");
        }

        logger.info("Buscando restaurante pelo nome: {}", nome);
        var restaurante = restauranteService.findByNomeRestaurante(nome);
        return ResponseEntity.ok(restaurante);
    }

    @Operation(description = "Salva restaurante",
            summary = "Salva restaurante",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do restaurante a ser criado",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "204"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @PostMapping
    public ResponseEntity<ResponseRestauranteRecord> saveRestaurante(@RequestBody CreateRestauranteRecord createRestauranteRecord) {

        //logger.info("Buscando restaurante pelo nome: {}", nome);
        ResponseRestauranteRecord response = restauranteService.criar(createRestauranteRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Update do restaurante por id",
            summary = "Update do restaurante por id",
            parameters = {
                    @Parameter(name = "id", description = "ID do restaurante", example = "1"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update de restaurante por id",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRestaurante(@PathVariable("id") Long id,
                                                  @Valid @RequestBody UpdateRestauranteRecord updateRestauranteRecord) {
        logger.info("Atualizando restaurante ID: {}", id);
        restauranteService.atualizar(id, updateRestauranteRecord);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delecao de restaurante por id",
            summary = "Delecao de restaurante por id",
            parameters = {
                    @Parameter(name = "id", description = "ID do restaurante", example = "1"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Delecao de restaurante por id",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurante(@PathVariable("id") Long id) {
        logger.info("Excluindo restaurante ID: {}", id);
        restauranteService.deletar(id);
        return ResponseEntity.status(204).build();
    }

}
