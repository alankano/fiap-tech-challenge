package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.dto.CreateTipoUsuarioRecord;
import br.com.fiap.techchallenge.dto.UpdateTipoUsuarioRecord;
import br.com.fiap.techchallenge.entities.TipoUsuario;
import br.com.fiap.techchallenge.services.TipoUsuarioService;
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
@RequestMapping("/tipo-usuario")
@Tag(name = "Tipo de Usuário", description = "API de gerenciamento de restaurantes")
public class TipoUsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(TipoUsuarioController.class);

    @Autowired
    private final TipoUsuarioService tipoUsuarioService;

    public TipoUsuarioController(TipoUsuarioService tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    @Operation(description = "Buscar todos os tipos com paginação",
            summary = "Busca todos os tipos",
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
    public ResponseEntity<List<TipoUsuario>> findAllTipos(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {

        if (page == null || size == null) {
            throw new BadRequestException("Parâmetros 'page' e 'size' são obrigatórios");
        }
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Parâmetros inválidos: 'page' deve ser >= 0 e 'size' > 0");
        }

        logger.info("Buscando tipos de usuários - Página: {}, Tamanho: {}", page, size);
        var tipoUsuarios   = tipoUsuarioService.acharTodosOsTipos(page, size);
        return ResponseEntity.ok(tipoUsuarios);
    }

    @PostMapping
    public ResponseEntity<TipoUsuario> saveTipoUsuario(@Valid @RequestBody
                                                       CreateTipoUsuarioRecord createTipoUsuarioRecord) {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setTipo(createTipoUsuarioRecord.getTipo());

        TipoUsuario saved = tipoUsuarioService.criar(tipoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(description = "Update do tipo de usuário por id",
            summary = "Update do tipo de usuário por id",
            parameters = {
                    @Parameter(name = "id", description = "ID do tipo de usuário", example = "1"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update de tipo de usuário por id",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTipoUsuario(@PathVariable("id") Long id,
                                           @Valid
                                           @RequestBody
                                           UpdateTipoUsuarioRecord updateTipoUsuarioRecord) {
        logger.info("Atualizando item ID: {}", id);
        tipoUsuarioService.atualizar(id, updateTipoUsuarioRecord);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delecao de tipo de usuário por id",
            summary = "Delecao de tipo de usuário por id",
            parameters = {
                    @Parameter(name = "id", description = "ID do tipo de usuário", example = "1"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Delecao de tipo de usuário por id",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipo(@PathVariable("id") Long id) {
        logger.info("Excluindo restaurante ID: {}", id);
        tipoUsuarioService.deletar(id);
        return ResponseEntity.status(204).build();
    }


}
