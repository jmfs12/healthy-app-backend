package com.backend.java.saude_e_bem_estar.service;

import com.backend.java.saude_e_bem_estar.entities.Usuario;
import com.backend.java.saude_e_bem_estar.repository.UsuarioRepository;
import com.backend.java.saude_e_bem_estar.exceptions.UsuarioNaoEncontradoException;
import com.backend.java.saude_e_bem_estar.exceptions.EmailJaCadastradoException;
import com.backend.java.saude_e_bem_estar.exceptions.CpfJaCadastradoException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario criar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new EmailJaCadastradoException();
        }
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new CpfJaCadastradoException();
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());
    }

    public Usuario atualizar(Long id, Usuario novosDados) {
        Usuario usuarioExistente = buscarPorId(id);

        if (novosDados.getNome_completo() != null) {
            usuarioExistente.setNome_completo(novosDados.getNome_completo());
        }
        if (novosDados.getData_nascimento() != null) {
            usuarioExistente.setData_nascimento(novosDados.getData_nascimento());
        }
        if (novosDados.getTelefone() != null) {
            usuarioExistente.setTelefone(novosDados.getTelefone());
        }
        if (novosDados.getSenha() != null && !novosDados.getSenha().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(novosDados.getSenha()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public void deletar(Long id) {
        Usuario usuarioExistente = buscarPorId(id);
        usuarioRepository.delete(usuarioExistente);
    }
}
