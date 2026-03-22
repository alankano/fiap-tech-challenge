package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM itens LIMIT :size OFFSET :offset", nativeQuery = true)
    Optional <List<Item>> findByRestauranteId(Long restauranteId, @Param("size") int size, @Param("offset") int offset);

    java.util.Optional<Item> findByNome(String nome);
    java.util.Optional<Item> findByNomeAndRestauranteId(String nome, Long restauranteId);

    boolean existsByNome(String nome);
    boolean existsByNomeAndRestauranteId(String nome, Long restauranteId);

    // Check if a Restaurante with given id exists (querying the Restaurante entity)
    @Query("select case when count(r)>0 then true else false end from Restaurante r where r.id = :id")
    boolean existsRestauranteById(@Param("id") Long id);
}
