package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.entities.Usuario;
import br.com.fiap.techchallenge.services.UsuarioService;
import br.com.fiap.techchallenge.services.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "API de gerenciamento de usuários")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(description = "Buscar todos os usuarios com paginação",
            summary = "Busca todos os usuarios",
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
    public ResponseEntity<List<Usuario>> findAllUsuarios(  @RequestParam("page") Integer page,
                                                           @RequestParam("size") Integer size) {

        if (page == null || size == null) {
            throw new BadRequestException("Parâmetros 'page' e 'size' são obrigatórios");
        }
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Parâmetros inválidos: 'page' deve ser >= 0 e 'size' > 0");
        }

        logger.info("Buscando usuarios - Página: {}, Tamanho: {}", page, size);
        var usuarios = usuarioService.findAllUsuario(page, size);
        return ResponseEntity.ok(usuarios);
    }

    @Operation(description = "Busca usuario por nome",
            summary = "Busca usuario por nome",
            parameters = {
                    @Parameter(name = "nome", description = "Nome do usuario", example = "alan"),
            },
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")
            }
    )
    @GetMapping("/buscaNome")
    public ResponseEntity<List<Usuario>> findUsuarioByNome(@RequestParam("nome") String nome) {
        if (nome == null ) {
            throw new BadRequestException("Parâmetro 'nome' é obrigatório");
        }

        logger.info("Buscando usuario por nome: {}", nome);
        var usuario = usuarioService.findByNomeUsuario(nome);
        return ResponseEntity.ok(usuario);
    }

    @Operation(description = "Busca usuario por id",
            summary = "Busca usuario por id",
            parameters = {
                    @Parameter(name = "id", description = "ID do usuario", example = "1"),
            },
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> findUsuarioById(@PathVariable("id") Long id) {
        if (id == null) {
            throw new BadRequestException("Parâmetro id é obrigatório");
        }

        logger.info("Buscando usuario por ID: {}", id);
        var usuario = usuarioService.findByIdUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(description = "Busca usuario por id",
            summary = "Busca usuario por id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do usuário a ser criado",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @PostMapping
    public ResponseEntity<Void> saveUsuario(@Valid @RequestBody Usuario usuario) {
        logger.info("Salvando usuario : {}", usuario.getNome());
        usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(201).build();
    }

    @Operation(description = "Update de usuario por id",
            summary = "Update de usuario por id",
            parameters = {
                    @Parameter(name = "id", description = "ID do usuario", example = "1"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update de usuario por id",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUsuario(@PathVariable("id") Long id,
                                              @RequestBody Usuario usuario) {
        logger.info("Atualizando usuario ID: {}", id);
        usuarioService.updateUsuario(usuario, id);
        var responseStatus = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(responseStatus.value()).build();
    }

    @Operation(description = "Delecao de usuario por id",
            summary = "Delecao de usuario por id",
            parameters = {
                    @Parameter(name = "id", description = "ID do usuario", example = "1"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Delecao de usuario por id",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Erro no envio dos parâmetros", responseCode = "400")

            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
        logger.info("Excluindo usuaio ID: {}", id);
        usuarioService.deleteUsuario(id);
        return ResponseEntity.status(204).build();
    }

}
