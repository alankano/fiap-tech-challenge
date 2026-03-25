package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Senha;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SenhaRepositoryImplTest {

    private SenhaRepositoryImpl repository;
    private org.springframework.jdbc.core.simple.JdbcClient jdbcClient;
    private br.com.fiap.techchallenge.loader.SqlQueryLoader sqlQueryLoader;

    @BeforeEach
    void setUp() throws Exception {
        jdbcClient = Mockito.mock(org.springframework.jdbc.core.simple.JdbcClient.class, Mockito.RETURNS_DEEP_STUBS);
        sqlQueryLoader = mock(br.com.fiap.techchallenge.loader.SqlQueryLoader.class);

        repository = new SenhaRepositoryImpl();
        var f1 = SenhaRepositoryImpl.class.getDeclaredField("jdbcClient");
        f1.setAccessible(true);
        f1.set(repository, jdbcClient);
        var f2 = SenhaRepositoryImpl.class.getDeclaredField("sqlQueryLoader");
        f2.setAccessible(true);
        f2.set(repository, sqlQueryLoader);
    }

    @Test
    void updateSenha_returnsInt() {
        when(sqlQueryLoader.loadQuery("senha/updateSenha")).thenReturn("sql-update");
        // ensure Senha has novaSenha so the param matches
        Senha s = new Senha(); s.setNovaSenha("new");

        when(jdbcClient.sql("sql-update").param("senha", "new").param("id", 1L).update()).thenReturn(1);

        Integer res = repository.updateSenha(s, 1L);
        assertEquals(1, res);

        verify(sqlQueryLoader).loadQuery("senha/updateSenha");
    }

    @Test
    void findSenhaById_returnsOptional_whenPresent() {
        when(sqlQueryLoader.loadQuery("senha/findSenhaById")).thenReturn("sql-find");
        when(jdbcClient.sql("sql-find").param("id", 1L).query(Senha.class).optional()).thenReturn(Optional.of(new Senha()));

        Optional<Senha> res = repository.findSenhaById(1L);
        assertTrue(res.isPresent());
    }

    @Test
    void findSenhaById_returnsEmpty_whenNotFound() {
        when(sqlQueryLoader.loadQuery("senha/findSenhaById")).thenReturn("sql-find");
        when(jdbcClient.sql("sql-find").param("id", 2L).query(Senha.class).optional()).thenReturn(Optional.empty());

        Optional<Senha> res = repository.findSenhaById(2L);
        assertTrue(res.isEmpty());
    }
}
