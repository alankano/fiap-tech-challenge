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

    @Test
    void findUsuarioByEmail_returnsOptional() {
        when(sqlQueryLoader.loadQuery("usuario/findUsuarioByEmail")).thenReturn("sql-email");
        when(jdbcClient.sql("sql-email").param("email", "a@b.com").query(Usuario.class).optional()).thenReturn(Optional.of(new Usuario()));

        Usuario u = new Usuario(); u.setEmail("a@b.com");
        var res = repository.findUsuarioByEmail(u);
        assertTrue(res.isPresent());
    }

    @Test
    void findUsuarioByNome_returnsList() {
        when(sqlQueryLoader.loadQuery("usuario/findUsuarioByNome")).thenReturn("sql-nome");
        when(jdbcClient.sql("sql-nome").param("nome", "joe").query(Usuario.class).list()).thenReturn(List.of(new Usuario()));

        var res = repository.findUsuarioByNome("joe");
        assertFalse(res.isEmpty());
    }

    @Test
    void saveUsuario_returnsInt() {
        when(sqlQueryLoader.loadQuery("usuario/saveUsuario")).thenReturn("sql-save");
        when(jdbcClient.sql("sql-save").param("nome", "n").param("email", "e").param("login", "l").param("senha", "s").param("endereco", "en").param("tipoUsuario", "t").update())
                .thenReturn(1);

        Usuario u = new Usuario();
        u.setNome("n"); u.setEmail("e"); u.setLogin("l"); u.setSenha("s"); u.setEndereco("en"); u.setTipoUsuario("t");

        Integer res = repository.saveUsuario(u);
        assertEquals(1, res);
    }

    @Test
    void updateUsuario_returnsInt() {
        when(sqlQueryLoader.loadQuery("usuario/updateUsuario")).thenReturn("sql-update");
        when(jdbcClient.sql("sql-update").param("nome", "n").param("email", "e").param("login", "l").param("endereco", "en").param("tipoUsuario", "t").param("id", 1L).update())
                .thenReturn(1);

        Usuario u = new Usuario();
        u.setNome("n"); u.setEmail("e"); u.setLogin("l"); u.setEndereco("en"); u.setTipoUsuario("t");

        Integer res = repository.updateUsuario(u, 1L);
        assertEquals(1, res);
    }

    @Test
    void deleteUsuario_returnsInt() {
        when(sqlQueryLoader.loadQuery("usuario/deleteUsuario")).thenReturn("sql-del");
        when(jdbcClient.sql("sql-del").param("id", 1L).update()).thenReturn(1);

        Integer res = repository.deleteUsuario(1L);
        assertEquals(1, res);
    }
}
