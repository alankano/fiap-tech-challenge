package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByRestauranteId(Long restauranteId);

    java.util.Optional<Item> findByNome(String nome);
    java.util.Optional<Item> findByNomeAndRestauranteId(String nome, Long restauranteId);

    boolean existsByNomeAndRestauranteId(String nome, Long restauranteId);
}
