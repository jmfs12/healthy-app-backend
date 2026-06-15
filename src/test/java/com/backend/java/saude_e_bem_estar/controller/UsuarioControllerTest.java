package com.backend.java.saude_e_bem_estar.controller;

import com.backend.java.saude_e_bem_estar.config.SecurityConfig;
import com.backend.java.saude_e_bem_estar.dto.UsuarioUpdateRequestDTO;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@Import({SecurityConfig.class, SecurityFilter.class})
class UsuarioControllerTest {

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
    void shouldReturnForbiddenWhenAccessingCrudWithoutToken() throws Exception {
        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnUserWhenAuthenticatedAndFound() throws Exception {
        Usuario user = new Usuario();
        user.setIdUsuario(1L);
        user.setEmail("joao@example.com");
        user.setNome_completo("João");

        when(tokenService.validateToken("valid-token")).thenReturn("joao@example.com");
        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(user));
        when(usuarioService.buscarPorId(1L)).thenReturn(user);

        mockMvc.perform(get("/usuarios/1")
                .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_usuario").value(1L))
                .andExpect(jsonPath("$.email").value("joao@example.com"));
    }

    @Test
    void shouldUpdateUserWhenAuthenticated() throws Exception {
        Usuario user = new Usuario();
        user.setIdUsuario(1L);
        user.setEmail("joao@example.com");
        user.setNome_completo("João");

        Usuario updatedUser = new Usuario();
        updatedUser.setIdUsuario(1L);
        updatedUser.setEmail("joao@example.com");
        updatedUser.setNome_completo("João Alterado");

        when(tokenService.validateToken("valid-token")).thenReturn("joao@example.com");
        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(user));
        when(usuarioService.atualizar(eq(1L), any(UsuarioUpdateRequestDTO.class))).thenReturn(updatedUser);

        UsuarioUpdateRequestDTO body = new UsuarioUpdateRequestDTO("João Alterado", null, null, null);

        mockMvc.perform(put("/usuarios/1")
                .header("Authorization", "Bearer valid-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome_completo").value("João Alterado"));
    }

    @Test
    void shouldDeleteUserWhenAuthenticated() throws Exception {
        Usuario user = new Usuario();
        user.setIdUsuario(1L);
        user.setEmail("joao@example.com");

        when(tokenService.validateToken("valid-token")).thenReturn("joao@example.com");
        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(user));
        doNothing().when(usuarioService).deletar(1L);

        mockMvc.perform(delete("/usuarios/1")
                .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).deletar(1L);
    }
}
