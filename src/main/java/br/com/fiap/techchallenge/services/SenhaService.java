package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.entities.Senha;
import br.com.fiap.techchallenge.repositories.SenhaRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidSenhaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SenhaService {

    @Autowired
    private final SenhaRepository senhaRepository;

    public SenhaService(SenhaRepository senhaRepository) {
        this.senhaRepository = senhaRepository;
    }

    public void updateSenha(Senha senha, Long id) {
        var senhaAtual = findSenhaById(id);
        if (senhaAtual.isPresent()) {
            if (!senhaAtual.get().getSenha().equals(senha.getSenha())) {
                throw new InvalidSenhaException("Senha atual incorreta.");
            }
        } else {
            throw new InvalidSenhaException("Senha não encontrada para o ID: " + id);
        }

        if (!senha.getNovaSenha().equals(senha.getNovaSenha2())) {
            throw new InvalidSenhaException("As novas senhas não coincidem.");
        }

        var update = senhaRepository.updateSenha(senha, id);
        if (update == 0) {
            throw new InvalidSenhaException("Senha não encontrado para atualização: " + id);
        }
    }

    public Optional<Senha> findSenhaById(Long id) {
        return Optional.ofNullable(senhaRepository.findSenhaById(id)
                .orElseThrow(() -> new InvalidSenhaException("Usuario não encontrado")));
    }
}
