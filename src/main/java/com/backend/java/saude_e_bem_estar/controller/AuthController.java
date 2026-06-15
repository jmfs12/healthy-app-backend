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
import com.backend.java.saude_e_bem_estar.exceptions.CustomErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para registro de novos usuários e autenticação (Login).")
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
    @Operation(summary = "Registra um novo usuário no sistema", description = "Cria uma nova conta de usuário. Não requer autenticação.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso",
            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados informados inválidos ou mal formatados",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":400,\"error\":\"Regra de negócio violada\",\"message\":\"O e-mail deve ser válido.\",\"path\":\"/auth/register\"}"))),
        @ApiResponse(responseCode = "409", description = "Conflito: CPF ou E-mail já cadastrado no sistema",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":409,\"error\":\"Regra de negócio violada\",\"message\":\"E-mail ou CPF já cadastrado.\",\"path\":\"/auth/register\"}")))
    })
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
    @Operation(summary = "Autentica um usuário", description = "Verifica credenciais e retorna o token JWT de acesso. Não requer autenticação.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso. Retorna o token JWT.",
            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados informados inválidos ou mal formatados",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":400,\"error\":\"Regra de negócio violada\",\"message\":\"O e-mail é obrigatório.\",\"path\":\"/auth/login\"}"))),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas (e-mail ou senha incorretos)",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"E-mail ou senha incorretos.\",\"path\":\"/auth/login\"}")))
    })
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
