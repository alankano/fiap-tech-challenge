 package br.com.fiap.techchallenge.repositories;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    @Test
    void interfaceHasExpectedMethods() throws Exception {
        Class<?> repo = Class.forName("br.com.fiap.techchallenge.repositories.ItemRepository");

        Method m1 = repo.getMethod("findByRestauranteId", Long.class, int.class, int.class);
        Method m2 = repo.getMethod("findByNome", String.class);
        Method m3 = repo.getMethod("findByNomeAndRestauranteId", String.class, Long.class);
        Method m4 = repo.getMethod("existsRestauranteById", Long.class);

        assertNotNull(m1);
        assertNotNull(m2);
        assertNotNull(m3);
        assertNotNull(m4);
    }
}

