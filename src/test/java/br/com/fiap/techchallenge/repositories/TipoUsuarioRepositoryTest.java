package br.com.fiap.techchallenge.repositories;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class TipoUsuarioRepositoryTest {

    @Test
    void interfaceHasExpectedMethods() throws Exception {
        Class<?> repo = Class.forName("br.com.fiap.techchallenge.repositories.TipoUsuarioRepository");
        Method m1 = repo.getMethod("findAllTipoUsuario", int.class, int.class);
        Method m2 = repo.getMethod("findByTipo", String.class);
        Method m3 = repo.getMethod("existsByTipo", String.class);

        assertNotNull(m1);
        assertNotNull(m2);
        assertNotNull(m3);
    }
}
