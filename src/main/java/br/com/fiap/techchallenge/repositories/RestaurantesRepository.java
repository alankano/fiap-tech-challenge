package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantesRepository extends JpaRepository<Restaurante, Long> {

    Optional<Restaurante> findByNome(String nome);

    List<Restaurante> findByEndereco(String endereco);

    List<Restaurante> findByTipoCozinha(String tipoCozinha);

    List<Restaurante> findAllByDiasFuncionamento(String diasFuncionamento);

    List<Restaurante> findAllByHorarioAbertura(String horarioAbertura);

    List<Restaurante> findAllByHorarioFechamento(String horarioFechamento);

    // Use a native query for pagination (limit/offset). Parameters are named to avoid method name parsing.
    @Query(value = "SELECT * FROM restaurantes LIMIT :size OFFSET :offset", nativeQuery = true)
    List<Restaurante> findAllRestaurante(@Param("size") int size, @Param("offset") int offset);

    boolean existsByNome(String nome);

    void deleteByNome(String nome);

}
