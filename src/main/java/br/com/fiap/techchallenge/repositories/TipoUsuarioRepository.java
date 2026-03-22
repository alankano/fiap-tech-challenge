package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {

    @Query(value = "SELECT * FROM tipo_usuario LIMIT :size OFFSET :offset", nativeQuery = true)
    List<TipoUsuario> findAllTipoUsuario(@Param("size") int size, @Param("offset") int offset);

    java.util.Optional<TipoUsuario> findByTipo(String tipo);

    boolean existsByTipo(String tipo);

}
