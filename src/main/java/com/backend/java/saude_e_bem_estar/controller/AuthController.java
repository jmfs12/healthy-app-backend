package com.backend.java.saude_e_bem_estar.controller;

import com.backend.java.saude_e_bem_estar.dto.LoginRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.LoginResponseDTO;
import com.backend.java.saude_e_bem_estar.dto.UsuarioRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.UsuarioResponseDTO;
import com.backend.java.saude_e_bem_estar.entities.Usuario;
import com.backend.java.saude_e_bem_estar.repository.UsuarioRepository;
import com.backend.java.saude_e_bem_estar.security.TokenService;
import com.backend.java.saude_e_bem_estar.service.UsuarioService;
import com.backend.java.saude_e_bem_estar.exceptions.CredenciaisInvalidasException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService,
                          UsuarioRepository usuarioRepository,
                          TokenService tokenService,
                          PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody @Valid UsuarioRequestDTO body) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome_completo(body.nome_completo());
        novoUsuario.setCpf(body.cpf());
        novoUsuario.setData_nascimento(body.data_nascimento());
        novoUsuario.setEmail(body.email());
        novoUsuario.setTelefone(body.telefone());
        novoUsuario.setSenha(body.senha());

        Usuario usuarioCriado = usuarioService.criar(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponseDTO.fromEntity(usuarioCriado));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO body) {
        Usuario usuario = usuarioRepository.findByEmail(body.email())
                .orElseThrow(() -> new CredenciaisInvalidasException());

        if (!passwordEncoder.matches(body.senha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        String token = tokenService.generateToken(usuario);
        return ResponseEntity.ok(new LoginResponseDTO(token, usuario.getEmail()));
    }
}
