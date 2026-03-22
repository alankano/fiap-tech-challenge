package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.dto.ResponseItemRecord;
import br.com.fiap.techchallenge.dto.ResponseTipoUsuarioRecord;
import br.com.fiap.techchallenge.dto.UpdateItemRecord;
import br.com.fiap.techchallenge.dto.UpdateTipoUsuarioRecord;
import br.com.fiap.techchallenge.entities.Item;
import br.com.fiap.techchallenge.entities.Restaurante;
import br.com.fiap.techchallenge.entities.TipoUsuario;
import br.com.fiap.techchallenge.repositories.TipoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoUsuarioService {

    @Autowired
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public TipoUsuarioService(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    public List<TipoUsuario> acharTodosOsTipos(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("page must be >= 0 and size > 0");
        }
        int offset = (page - 1) * size;
        return tipoUsuarioRepository.findAllTipoUsuario(size, offset);
    }

    @Transactional
    public TipoUsuario criar(TipoUsuario tipoUsuario) {

        if (tipoUsuarioRepository.existsByTipo(tipoUsuario.getTipo())) {
            throw new IllegalArgumentException("Tipo de usuario com este nome já existe!");
        }

        return tipoUsuarioRepository.save(tipoUsuario);

    }

    @Transactional
    public ResponseTipoUsuarioRecord atualizar(Long id, UpdateTipoUsuarioRecord updateTipoUsuarioRecord) {

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de usuário não encontrado!"));

        tipoUsuario.setTipo(updateTipoUsuarioRecord.getTipo());

        TipoUsuario salvo = tipoUsuarioRepository.save(tipoUsuario);

        return converterParaResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {

        if (!tipoUsuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Tipo de usuário não encontrado!");
        }

        tipoUsuarioRepository.deleteById(id);
    }

    private ResponseTipoUsuarioRecord converterParaResponse(TipoUsuario tipoUsuario) {
        return new ResponseTipoUsuarioRecord(tipoUsuario);
    }
}
