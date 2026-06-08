package com.backend.java.saude_e_bem_estar.controller;

import com.backend.java.saude_e_bem_estar.config.SecurityConfig;
import com.backend.java.saude_e_bem_estar.dto.LoginRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.UsuarioRequestDTO;
import com.backend.java.saude_e_bem_estar.entities.Usuario;
import com.backend.java.saude_e_bem_estar.repository.UsuarioRepository;
import com.backend.java.saude_e_bem_estar.security.SecurityFilter;
import com.backend.java.saude_e_bem_estar.security.TokenService;
import com.backend.java.saude_e_bem_estar.service.UsuarioService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class, SecurityFilter.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO(
                "João da Silva", "12345678901", LocalDate.of(1990, 1, 1),
                "joao@example.com", "81999999999", "password123"
        );

        Usuario createdUser = new Usuario();
        createdUser.setIdUsuario(1L);
        createdUser.setNome_completo(request.nome_completo());
        createdUser.setCpf(request.cpf());
        createdUser.setEmail(request.email());
        createdUser.setSenha("encodedPassword");

        when(usuarioService.criar(any(Usuario.class))).thenReturn(createdUser);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_usuario").value(1L))
                .andExpect(jsonPath("$.email").value("joao@example.com"));
    }

    @Test
    void shouldLoginSuccessfullyAndReturnToken() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("joao@example.com", "password123");

        Usuario user = new Usuario();
        user.setEmail(request.email());
        user.setSenha("encodedPassword");

        when(usuarioRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.senha(), user.getSenha())).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"))
                .andExpect(jsonPath("$.email").value("joao@example.com"));
    }

    @Test
    void shouldReturnBadRequestWhenLoginWithWrongCredentials() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("joao@example.com", "wrong-password");

        Usuario user = new Usuario();
        user.setEmail(request.email());
        user.setSenha("encodedPassword");

        when(usuarioRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.senha(), user.getSenha())).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
