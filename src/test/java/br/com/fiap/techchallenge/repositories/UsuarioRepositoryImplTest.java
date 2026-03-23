package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioRepositoryImplTest {

    private UsuarioRepositoryImpl repository;
    private org.springframework.jdbc.core.simple.JdbcClient jdbcClient;
    private br.com.fiap.techchallenge.loader.SqlQueryLoader sqlQueryLoader;

    @BeforeEach
    void setUp() throws Exception {
        jdbcClient = Mockito.mock(org.springframework.jdbc.core.simple.JdbcClient.class, Mockito.RETURNS_DEEP_STUBS);
        sqlQueryLoader = mock(br.com.fiap.techchallenge.loader.SqlQueryLoader.class);

        repository = new UsuarioRepositoryImpl();
        var f1 = UsuarioRepositoryImpl.class.getDeclaredField("jdbcClient");
        f1.setAccessible(true);
        f1.set(repository, jdbcClient);
        var f2 = UsuarioRepositoryImpl.class.getDeclaredField("sqlQueryLoader");
        f2.setAccessible(true);
        f2.set(repository, sqlQueryLoader);
    }

    @Test
    void findUsuarioById_returnsOptional() {
        when(sqlQueryLoader.loadQuery("usuario/findUsuarioById")).thenReturn("sql-id");
        when(jdbcClient.sql("sql-id").param("id", 2L).query(Usuario.class).optional()).thenReturn(Optional.of(new Usuario()));

        Optional<Usuario> res = repository.findUsuarioById(2L);
        assertTrue(res.isPresent());
    }

    @Test
    void findUsuarioAll_returnsList() {
        when(sqlQueryLoader.loadQuery("usuario/findUsuarioAll")).thenReturn("sql-all");
        when(jdbcClient.sql("sql-all").param("size", 10).param("offset", 0).query(Usuario.class).list()).thenReturn(List.of(new Usuario()));

        var res = repository.findUsuarioAll(10, 0);
        assertFalse(res.isEmpty());
    }
}

