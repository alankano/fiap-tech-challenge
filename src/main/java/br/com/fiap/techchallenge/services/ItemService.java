package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.dto.*;
import br.com.fiap.techchallenge.entities.Item;
import br.com.fiap.techchallenge.entities.Restaurante;
import br.com.fiap.techchallenge.repositories.ItemRepository;
import br.com.fiap.techchallenge.repositories.RestaurantesRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private final ItemRepository itemRepository;

    @Autowired
    private final RestaurantesRepository restaurantesRepository;

    public ItemService(ItemRepository itemRepository, RestaurantesRepository restaurantesRepository) {
        this.itemRepository = itemRepository;
        this.restaurantesRepository = restaurantesRepository;
    }

    public List<Item> acharItensPorRestaurante(Long restauranteId, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new InvalidItemException("page must be >= 0 and size > 0");
        }

        if (restauranteId == null || !itemRepository.existsRestauranteById(restauranteId)) {
            throw new InvalidItemException("Restaurante não encontrado!");
        }

        int offset = (page - 1) * size;
        return itemRepository.findByRestauranteId(restauranteId, size, offset).orElseThrow(() -> new InvalidItemException("Restaurante não encontrado"));
    }

    public Item findByNomeItem(String nome) {
        return itemRepository.findByNome(nome).orElseThrow(() -> new InvalidItemException("Item não encontrado"));
    }

    @Transactional
    public Item criar(Item item, Long restauranteId) {

        // Check uniqueness per restaurante
        if (itemRepository.existsByNomeAndRestauranteId(item.getNome(), restauranteId)) {
            throw new InvalidItemException("Item com este nome já existe neste restaurante!");
        }

        // validate restaurante exists using repository method
        if (restauranteId == null || !itemRepository.existsRestauranteById(restauranteId)) {
            throw new InvalidItemException("Restaurante não encontrado!");
        }

        // fetch restaurante and associate
        Restaurante restaurante = restaurantesRepository.findById(restauranteId)
                .orElseThrow(() -> new InvalidItemException("Restaurante não encontrado!"));

        item.setRestaurante(restaurante);
        return itemRepository.save(item);

    }

    @Transactional
    public ResponseItemRecord atualizar(Long id, UpdateItemRecord updateItemRecord) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new InvalidItemException("Item não encontrado!"));

        item.setNome(updateItemRecord.getNome());
        item.setDescricao(updateItemRecord.getDescricao());
        item.setPreco(updateItemRecord.getPreco());
        item.setDisponibilidade(updateItemRecord.getDisponibilidade());
        item.setImagem(updateItemRecord.getImagem());


        Item salvo = itemRepository.save(item);

        return converterParaResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {

        if (!itemRepository.existsById(id)) {
            throw new InvalidItemException("Item não encontrado!");
        }

        itemRepository.deleteById(id);
    }

    private ResponseItemRecord converterParaResponse(Item item) {
        return new ResponseItemRecord(item);
    }

}
