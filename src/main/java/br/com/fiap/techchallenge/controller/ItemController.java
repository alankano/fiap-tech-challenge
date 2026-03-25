package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.dto.CreateItemRecord;
import br.com.fiap.techchallenge.dto.ResponseItemRecord;
import br.com.fiap.techchallenge.dto.UpdateItemRecord;
import br.com.fiap.techchallenge.entities.Item;
import br.com.fiap.techchallenge.services.ItemService;
import br.com.fiap.techchallenge.services.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
@Tag(name = "Itens", description = "API de gerenciamento de itens do restaurante")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(description = "Buscar todos os itens do restaurante com paginação",
            summary = "Busca todos os itens",
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

    @GetMapping("/restaurantes/{restauranteId}")
    public ResponseEntity<List<Item>> findItensByRestaurante(@Valid
                                                             @PathVariable("restauranteId") Long restauranteId,
                                                             @RequestParam("page") Integer page,
                                                             @RequestParam("size") Integer size) {
        if (restauranteId == null) throw new BadRequestException("restauranteId é obrigatório");
        var itens = itemService.acharItensPorRestaurante(restauranteId, page, size);
        return ResponseEntity.ok(itens);
    }

    @Operation(description = "Buscar itens pelo nome",
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
    public ResponseEntity<Item> findItemByNome(@Valid @RequestParam("nome") String nome) {

        if (nome == null || nome.isBlank()) {
            throw new BadRequestException("Parâmetro 'nome' é obrigatório");
        }

        logger.info("Buscando item pelo nome: {}", nome);
        var item = itemService.findByNomeItem(nome);
        return ResponseEntity.ok(item);
    }

    @Operation(description = "Salva item do restaurante",
            summary = "Salva item",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do item a ser criado",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "204"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @PostMapping
    public ResponseEntity<Item> saveItem(@RequestParam("restauranteId") Long restauranteId,
                                         @Valid @RequestBody CreateItemRecord createItemRecord) {
        Item item = new Item();
        item.setNome(createItemRecord.getNome());
        item.setDescricao(createItemRecord.getDescricao());
        item.setPreco(createItemRecord.getPreco());
        item.setDisponibilidade(createItemRecord.getDisponibilidade());
        item.setImagem(createItemRecord.getImagem());

        Item saved = itemService.criar(item, restauranteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(description = "Update do item por id",
            summary = "Update do item por id",
            parameters = {
                    @Parameter(name = "id", description = "ID do item", example = "1"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update de item por id",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable("id") Long id,
                                           @Valid
                                           @RequestBody
                                           UpdateItemRecord updateItemRecord) {
        logger.info("Atualizando item ID: {}", id);
        itemService.atualizar(id, updateItemRecord);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delecao de item por id",
            summary = "Delecao de item por id",
            parameters = {
                    @Parameter(name = "id", description = "ID do item", example = "1"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Delecao de item por id",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id) {
        logger.info("Excluindo restaurante ID: {}", id);
        itemService.deletar(id);
        return ResponseEntity.status(204).build();
    }

}
