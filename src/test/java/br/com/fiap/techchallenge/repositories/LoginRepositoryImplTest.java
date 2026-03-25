package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

class LoginRepositoryImplTest {

    private LoginRepositoryImpl repository;

    @Mock
    private org.springframework.jdbc.core.simple.JdbcClient jdbcClient;

    @Mock
    private br.com.fiap.techchallenge.loader.SqlQueryLoader sqlQueryLoader;

    @BeforeEach
    void setUp() {
        jdbcClient = Mockito.mock(org.springframework.jdbc.core.simple.JdbcClient.class, Mockito.RETURNS_DEEP_STUBS);
        sqlQueryLoader = mock(br.com.fiap.techchallenge.loader.SqlQueryLoader.class);
        repository = new LoginRepositoryImpl();
        // inject mocks via reflection since fields are package-private autowired
        java.lang.reflect.Field f1;
        try {
            f1 = LoginRepositoryImpl.class.getDeclaredField("jdbcClient");
            f1.setAccessible(true);
            f1.set(repository, jdbcClient);

            java.lang.reflect.Field f2 = LoginRepositoryImpl.class.getDeclaredField("sqlQueryLoader");
            f2.setAccessible(true);
            f2.set(repository, sqlQueryLoader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void validaLogin_returnsOptionalWhenFound() {
        Login sample = new Login();
        sample.setLogin("user");
        sample.setSenha("pass");

        when(sqlQueryLoader.loadQuery("login/validaLogin")).thenReturn("select 1");
        when(jdbcClient.sql("select 1").param("login", "user").param("senha", "pass").query(Login.class).optional())
                .thenReturn(Optional.of(sample));

        Optional<Login> res = repository.validaLogin(sample);
        assertTrue(res.isPresent());
        assertEquals("user", res.get().getLogin());

        verify(sqlQueryLoader).loadQuery("login/validaLogin");
        // don't verify chained param() calls (they were invoked during stubbing and during execution)
    }
}
