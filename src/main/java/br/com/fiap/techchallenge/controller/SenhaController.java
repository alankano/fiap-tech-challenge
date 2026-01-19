package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.entities.Senha;
import br.com.fiap.techchallenge.entities.Usuario;
import br.com.fiap.techchallenge.services.SenhaService;
import br.com.fiap.techchallenge.services.UsuarioService;
import br.com.fiap.techchallenge.services.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/senha")
@Tag(name = "Senha", description = "API de alteracao de senha")
public class SenhaController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private final SenhaService senhaService;

    public SenhaController(SenhaService senhaService) {
        this.senhaService = senhaService;
    }

    @Operation(description = "Atualizar senha de usuario",
            summary = "Atualiza a senha de um usuario",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Senha a ser trocada e senhas novas",
                    required = true
            ),
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500"),
                    @ApiResponse(description = "Senha inválida", responseCode = "400"),
                    @ApiResponse(description = "AS duas novas senhas não são iguais", responseCode = "400")
            }
    )

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSenha(@Valid @PathVariable("id") Long id,
                                              @RequestBody Senha senha) {
        if (id == null) {
            throw new BadRequestException("Parâmetro 'id' é obrigatório");
        }

        logger.info("Atualizando senha do usuario");
        senhaService.updateSenha(senha, id);
        var responseStatus = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(responseStatus.value()).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateSenhaSemId() {
        throw new BadRequestException("Parâmetro 'id' é obrigatório na URL. Use /path/{id}");
    }


}
