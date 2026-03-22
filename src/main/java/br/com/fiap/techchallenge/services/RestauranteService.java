package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.dto.CreateRestauranteRecord;
import br.com.fiap.techchallenge.dto.ResponseRestauranteRecord;
import br.com.fiap.techchallenge.dto.UpdateRestauranteRecord;
import br.com.fiap.techchallenge.entities.Restaurante;
import br.com.fiap.techchallenge.repositories.RestaurantesRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidRestauranteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private final RestaurantesRepository restaurantesRepository;

    public RestauranteService(RestaurantesRepository restaurantesRepository) {
        this.restaurantesRepository = restaurantesRepository;
    }

    public List<Restaurante> findAllRestaurantes(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("page must be >= 0 and size > 0");
        }
        int offset = (page - 1) * size;
        return restaurantesRepository.findAllRestaurante(size, offset);
    }

    public Restaurante findByNomeRestaurante(String nome) {
        return restaurantesRepository.findByNome(nome).orElseThrow(() -> new InvalidRestauranteException("Restaurante não encontrado"));
    }

    @Transactional
    public ResponseRestauranteRecord criar(CreateRestauranteRecord createRestauranteRecord) {

        if (restaurantesRepository.existsByNome(createRestauranteRecord.getNome())) {
            throw new IllegalArgumentException("Restaurante com este nome já existe!");
        }

        // Criar entidade
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(createRestauranteRecord.getNome());
        restaurante.setEndereco(createRestauranteRecord.getEndereco());
        restaurante.setTipoCozinha(createRestauranteRecord.getTipoCozinha());
        restaurante.setDiasFuncionamento(createRestauranteRecord.getDiasFuncionamento());
        restaurante.setHorarioAbertura(createRestauranteRecord.getHorarioAbertura());
        restaurante.setHorarioFechamento(createRestauranteRecord.getHorarioFechamento());
        restaurante.setIdUsuario(createRestauranteRecord.getIdUsuario());
        // Salvar
        Restaurante salvo = restaurantesRepository.save(restaurante);

        return converterParaResponse(salvo);
    }

    @Transactional
    public ResponseRestauranteRecord atualizar(Long id, UpdateRestauranteRecord updateRestauranteRecord) {

        Restaurante restaurante = restaurantesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado!"));

        restaurante.setNome(updateRestauranteRecord.getNome());
        restaurante.setEndereco(updateRestauranteRecord.getEndereco());
        restaurante.setTipoCozinha(updateRestauranteRecord.getTipoCozinha());
        restaurante.setDiasFuncionamento(updateRestauranteRecord.getDiasFuncionamento());
        restaurante.setHorarioAbertura(updateRestauranteRecord.getHorarioAbertura());
        restaurante.setHorarioFechamento(updateRestauranteRecord.getHorarioFechamento());
        restaurante.setIdUsuario(updateRestauranteRecord.getIdUsuario());

        Restaurante salvo = restaurantesRepository.save(restaurante);

        return converterParaResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {

        if (!restaurantesRepository.existsById(id)) {
            throw new IllegalArgumentException("Restaurante não encontrado!");
        }

        restaurantesRepository.deleteById(id);
    }

    private ResponseRestauranteRecord converterParaResponse(Restaurante restaurante) {
        return new ResponseRestauranteRecord(restaurante);
    }

}
