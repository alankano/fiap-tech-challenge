package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.entities.Login;
import br.com.fiap.techchallenge.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Tag(name = "Login", description = "API de validacao de login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(description = "Validacao de login",
            summary = "Validacao de login",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Erro interno do servidor", responseCode = "500")
            }
    )

    @PostMapping
    public ResponseEntity<Void> validaLogin(@RequestBody Login login) {
        logger.info("Validando login do usuario");
        loginService.validaLogin(login);
        var responseStatus = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(responseStatus.value()).build();
    }

}
