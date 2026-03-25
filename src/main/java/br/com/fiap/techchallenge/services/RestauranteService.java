package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.dto.CreateRestauranteRecord;
import br.com.fiap.techchallenge.dto.ResponseRestauranteRecord;
import br.com.fiap.techchallenge.dto.UpdateRestauranteRecord;
import br.com.fiap.techchallenge.entities.Restaurante;
import br.com.fiap.techchallenge.repositories.RestaurantesRepository;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidRestauranteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private final RestaurantesRepository restaurantesRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public RestauranteService(RestaurantesRepository restaurantesRepository, UsuarioRepository usuarioRepository) {
        this.restaurantesRepository = restaurantesRepository;
        this.usuarioRepository = usuarioRepository;
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
            throw new InvalidRestauranteException("Restaurante com este nome já existe!");
        }

        // Criar entidade
        // defensive validation: ensure nome is not blank
        if (createRestauranteRecord.getNome() == null || createRestauranteRecord.getNome().isBlank()) {
            throw new InvalidRestauranteException("Nome do restaurante é obrigatório");
        }

        // validate idUsuario exists (idUsuario is provided as String in DTO)
        if (createRestauranteRecord.getIdUsuario() == null || createRestauranteRecord.getIdUsuario().isBlank()) {
            throw new InvalidRestauranteException("idUsuario é obrigatório");
        }
        Long usuarioId;
        try {
            usuarioId = Long.parseLong(createRestauranteRecord.getIdUsuario());
        } catch (NumberFormatException ex) {
            throw new InvalidRestauranteException("idUsuario inválido");
        }
        if (usuarioRepository.findUsuarioById(usuarioId).isEmpty()) {
            throw new InvalidRestauranteException("Usuário não encontrado!");
        }

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

        // validate idUsuario in update as well
        if (updateRestauranteRecord.getIdUsuario() == null || updateRestauranteRecord.getIdUsuario().isBlank()) {
            throw new InvalidRestauranteException("idUsuario é obrigatório");
        }
        Long usuarioIdUpdate;
        try {
            usuarioIdUpdate = Long.parseLong(updateRestauranteRecord.getIdUsuario());
        } catch (NumberFormatException ex) {
            throw new InvalidRestauranteException("idUsuario inválido");
        }
        if (usuarioRepository.findUsuarioById(usuarioIdUpdate).isEmpty()) {
            throw new InvalidRestauranteException("Usuário não encontrado!");
        }

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
            throw new InvalidRestauranteException("Restaurante não encontrado!");
        }

        restaurantesRepository.deleteById(id);
    }

    private ResponseRestauranteRecord converterParaResponse(Restaurante restaurante) {
        return new ResponseRestauranteRecord(restaurante);
    }

}
