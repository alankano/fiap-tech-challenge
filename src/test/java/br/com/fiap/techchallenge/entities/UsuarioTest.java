package br.com.fiap.techchallenge.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void gettersAndSetters() {

        LocalDate dataUltimaAlteracao = LocalDate.now();

        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setNome("alan");
        usuario.setEmail("alan@example.com");
        usuario.setLogin("alan123");
        usuario.setSenha("123alan");
        usuario.setDataUltimaAlteracao(dataUltimaAlteracao);
        usuario.setEndereco("endereco");
        usuario.setTipoUsuario("tipoUsuario");

        assertEquals(3L, usuario.getId());
        assertEquals("alan", usuario.getNome());
        assertEquals("alan@example.com", usuario.getEmail());
        assertEquals("alan123", usuario.getLogin());
        assertEquals("123alan", usuario.getSenha());
        assertEquals(dataUltimaAlteracao, usuario.getDataUltimaAlteracao());
        assertEquals("endereco", usuario.getEndereco());
        assertEquals("tipoUsuario", usuario.getTipoUsuario());
    }
}

