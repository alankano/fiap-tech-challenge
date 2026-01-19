package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Usuario;
import br.com.fiap.techchallenge.loader.SqlQueryLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    @Autowired
    private JdbcClient jdbcClient;

    @Autowired
    private SqlQueryLoader sqlQueryLoader;

    @Override
    public Optional<Usuario> findUsuarioById(Long id) {
        String sql = sqlQueryLoader.loadQuery("usuario/findUsuarioById");

        return jdbcClient.sql(sql)
                .param("id", id)
                .query(Usuario.class)
                .optional();
    }

    @Override
    public Optional<Usuario> findUsuarioByEmail(Usuario usuario) {
        String sql = sqlQueryLoader.loadQuery("usuario/findUsuarioByEmail");
        return jdbcClient.sql(sql)
                .param("email", usuario.getEmail())
                .query(Usuario.class)
                .optional();
    }

    @Override
    public List<Usuario> findUsuarioByNome(String nome) {
        String sql = sqlQueryLoader.loadQuery("usuario/findUsuarioByNome");

        return jdbcClient.sql(sql)
                .param("nome", nome.toLowerCase())
                .query(Usuario.class)
                .list();
    }

    @Override
    public List<Usuario> findUsuarioAll(int size, int offset) {
        String sql = sqlQueryLoader.loadQuery("usuario/findUsuarioAll");
        return jdbcClient.sql(sql)
                .param("size", size)
                .param("offset", offset)
                .query(Usuario.class)
                .list();
    }

    @Override
    public Integer saveUsuario(Usuario usuario) {
        String sql = sqlQueryLoader.loadQuery("usuario/saveUsuario");

        return jdbcClient.sql(sql)
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("senha", usuario.getSenha())
                .param("endereco", usuario.getEndereco())
                .param("tipoUsuario", usuario.getTipoUsuario())
                .update();
    }

    @Override
    public Integer updateUsuario(Usuario usuario, Long id) {
        String sql = sqlQueryLoader.loadQuery("usuario/updateUsuario");

        return jdbcClient.sql(sql)
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("endereco", usuario.getEndereco())
                .param("tipoUsuario", usuario.getTipoUsuario())
                .param("id", id)
                .update();
    }

    @Override
    public Integer deleteUsuario(Long id) {
        String sql = sqlQueryLoader.loadQuery("usuario/deleteUsuario");
        return jdbcClient.sql(sql)
                .param("id", id)
                .update();
    }

}
