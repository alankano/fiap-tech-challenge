package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Senha;
import br.com.fiap.techchallenge.entities.Usuario;
import br.com.fiap.techchallenge.loader.SqlQueryLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SenhaRepositoryImpl implements SenhaRepository {

    @Autowired
    private JdbcClient jdbcClient;

    @Autowired
    private SqlQueryLoader sqlQueryLoader;

    @Override
    public Integer updateSenha(Senha senha, Long id) {
        String sql = sqlQueryLoader.loadQuery("senha/updateSenha");

        return jdbcClient.sql(sql)
                .param("senha", senha.getNovaSenha())
                .param("id", id)
                .update();
    }

    @Override
    public Optional<Senha> findSenhaById(Long id) {
        String sql = sqlQueryLoader.loadQuery("senha/findSenhaById");

        return jdbcClient.sql(sql)
                .param("id", id)
                .query(Senha.class)
                .optional();
    }

}
