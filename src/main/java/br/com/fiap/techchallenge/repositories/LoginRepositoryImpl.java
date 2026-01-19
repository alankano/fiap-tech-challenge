package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Login;
import br.com.fiap.techchallenge.loader.SqlQueryLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginRepositoryImpl implements LoginRepository {

    @Autowired
    private JdbcClient jdbcClient;

    @Autowired
    private SqlQueryLoader sqlQueryLoader;

    @Override
    public Optional<Login> validaLogin(Login login) {
        String sql = sqlQueryLoader.loadQuery("login/validaLogin");

        return jdbcClient.sql(sql)
                .param("login", login.getLogin())
                .param("senha", login.getSenha())
                .query(Login.class)
                .optional();
    }

}
