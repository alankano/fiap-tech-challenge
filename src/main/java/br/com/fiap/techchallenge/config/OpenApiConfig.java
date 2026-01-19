package br.com.fiap.techchallenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI locatech() {
        return new OpenAPI().info(
                new Info().title("API Backend Restaurant")
                        .description("API RESTful para gerenciamento de um restaurante, incluindo funcionalidades para gerenciar menu, pedidos, clientes e reservas.")
                        .version("1.0.0")
        );
    }
}
