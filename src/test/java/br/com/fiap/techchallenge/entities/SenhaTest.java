package br.com.fiap.techchallenge.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SenhaTest {

    @Test
    void gettersAndSetters() {

        LocalDate dataUltimaAlteracao = LocalDate.now();

        Senha senha = new Senha();
        senha.setId(1L);
        senha.setDataUltimaAlteracao(dataUltimaAlteracao);
        senha.setSenha("123");
        senha.setNovaSenha("novasenha");
        senha.setNovaSenha2("novasenha");

        assertEquals(1L, senha.getId());
        assertEquals(dataUltimaAlteracao, senha.getDataUltimaAlteracao());
        assertEquals("123", senha.getSenha());
        assertEquals("novasenha", senha.getNovaSenha());
        assertEquals("novasenha", senha.getNovaSenha2());

    }
}
