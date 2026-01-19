package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Senha;
import br.com.fiap.techchallenge.entities.Usuario;

import java.util.Optional;

public interface SenhaRepository {

    Integer updateSenha(Senha senha, Long id);
    Optional<Senha> findSenhaById(Long id);

}
