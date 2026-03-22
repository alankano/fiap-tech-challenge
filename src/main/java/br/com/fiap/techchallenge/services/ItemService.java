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

//    public List<Item> acharItens(int page, int size) {
//        if (page < 0 || size <= 0) {
//            throw new IllegalArgumentException("page must be >= 0 and size > 0");
//        }
//        int offset = (page - 1) * size;
//        return itemRepository.findAllItem(size, offset);
//    }

    public List<Item> acharItensPorRestaurante(Long restauranteId, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("page must be >= 0 and size > 0");
        }
        int offset = (page - 1) * size;
        return itemRepository.findByRestauranteId(restauranteId);
    }

    public Item findByNomeItem(String nome) {
        return itemRepository.findByNome(nome).orElseThrow(() -> new InvalidItemException("Item não encontrado"));
    }

    @Transactional
    public Item criar(Item item, Long restauranteId) {

        // Check uniqueness per restaurante
        if (itemRepository.existsByNomeAndRestauranteId(item.getNome(), restauranteId)) {
            throw new IllegalArgumentException("Item com este nome já existe neste restaurante!");
        }

        Restaurante restaurante = restaurantesRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado!"));

        item.setRestaurante(restaurante);
        return itemRepository.save(item);

    }

    @Transactional
    public ResponseItemRecord atualizar(Long id, UpdateItemRecord updateItemRecord) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado!"));

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
            throw new IllegalArgumentException("Restaurante não encontrado!");
        }

        itemRepository.deleteById(id);
    }

    private ResponseItemRecord converterParaResponse(Item item) {
        return new ResponseItemRecord(item);
    }

}
