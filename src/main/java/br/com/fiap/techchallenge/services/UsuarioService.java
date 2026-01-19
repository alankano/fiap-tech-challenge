package br.com.fiap.techchallenge.services;


import br.com.fiap.techchallenge.entities.Usuario;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.services.exceptions.InvalidUsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAllUsuario(int page, int size) {
        int offset = (page - 1) * size;
        return usuarioRepository.findUsuarioAll(size, offset);
    }

    public Optional<Usuario> findByIdUsuario(Long id) {
        return Optional.ofNullable(usuarioRepository.findUsuarioById(id)
                .orElseThrow(() -> new InvalidUsuarioException("Usuario não encontrado")));
    }

    public List<Usuario> findByNomeUsuario(String nome) {
        return usuarioRepository.findUsuarioByNome(nome);
    }

    public void saveUsuario(Usuario usuario) {
        var emailExistente = findByEmail(usuario);
        if (emailExistente.isPresent()) {
            throw new InvalidUsuarioException("Email já cadastrado: " + usuario.getEmail());
        }

        String nome = usuario.getNome();
        String email = usuario.getEmail();
        String login = usuario.getLogin();
        String senha = usuario.getSenha();
        String endereco = usuario.getEndereco();
        String tipoUsuario = usuario.getTipoUsuario();

        if (nome == null || nome.isEmpty() ||
            email == null || email.isEmpty() ||
            login == null || login.isEmpty() ||
            senha == null || senha.isEmpty() ||
            endereco == null || endereco.isEmpty() ||
            tipoUsuario == null || tipoUsuario.isEmpty()) {
            throw new InvalidUsuarioException("Todos os campos são obrigatórios para salvar o usuário.");
        }

        var save = usuarioRepository.saveUsuario(usuario);
//        if (save == 1) {
//            throw new InvalidUsuarioException("Erro ao salvar a usuario " + usuario.getNome());
//        }
        Assert.state(save == 1, "Erro ao salvar a usuario" + usuario.getNome());
    }

    public void updateUsuario(Usuario usuario, Long id) {
        var update = usuarioRepository.updateUsuario(usuario, id);
        if (update == 0) {
            throw new InvalidUsuarioException("Usuario não encontrado para atualização: " + id);
        }
    }

    public void deleteUsuario(Long id) {
        var delete = usuarioRepository.deleteUsuario(id);
        if (delete == 0) {
            throw new InvalidUsuarioException("Usuario não encontrada para exclusão: " + id);
        }
    }

    public Optional<Usuario> findByEmail(Usuario usuario) {
        return usuarioRepository.findUsuarioByEmail(usuario);
    }

}
