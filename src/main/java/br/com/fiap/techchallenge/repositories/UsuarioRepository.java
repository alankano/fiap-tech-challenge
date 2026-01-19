package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Usuario;


import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findUsuarioById(Long id);
    Optional<Usuario> findUsuarioByEmail(Usuario usuario);
    List<Usuario> findUsuarioByNome(String nome);
    List<Usuario> findUsuarioAll(int size, int offset);
    Integer saveUsuario(Usuario usuario);
    Integer updateUsuario(Usuario usuario, Long id);
    Integer deleteUsuario(Long id);

}
