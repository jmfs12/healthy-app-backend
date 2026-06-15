package com.backend.java.saude_e_bem_estar.service;

import com.backend.java.saude_e_bem_estar.entities.Usuario;
import com.backend.java.saude_e_bem_estar.dto.UsuarioUpdateRequestDTO;
import com.backend.java.saude_e_bem_estar.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNome_completo("João da Silva");
        usuario.setCpf("12345678901");
        usuario.setEmail("joao@example.com");
        usuario.setTelefone("81999999999");
        usuario.setData_nascimento(LocalDate.of(1990, 1, 1));
        usuario.setSenha("rawPassword");
    }

    @Test
    void shouldCreateUsuarioSuccessfully() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByCpf(usuario.getCpf())).thenReturn(false);
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario result = usuarioService.criar(usuario);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getSenha());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.criar(usuario));
        assertEquals("E-mail já cadastrado.", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void shouldThrowExceptionWhenCpfAlreadyExists() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByCpf(usuario.getCpf())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.criar(usuario));
        assertEquals("CPF já cadastrado.", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void shouldFindUsuarioById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdUsuario());
        assertEquals("joao@example.com", result.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUsuarioNotFoundById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.buscarPorId(1L));
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    void shouldUpdateUsuarioSuccessfully() {
        UsuarioUpdateRequestDTO novosDados = new UsuarioUpdateRequestDTO(
            "João da Silva Alterado",
            LocalDate.of(1991, 2, 2),
            "81888888888",
            null
        );

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario result = usuarioService.atualizar(1L, novosDados);

        assertNotNull(result);
        assertEquals("João da Silva Alterado", result.getNome_completo());
        assertEquals("81888888888", result.getTelefone());
        assertEquals(LocalDate.of(1991, 2, 2), result.getData_nascimento());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void shouldDeleteUsuarioSuccessfully() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        assertDoesNotThrow(() -> usuarioService.deletar(1L));

        verify(usuarioRepository, times(1)).delete(usuario);
    }
}
