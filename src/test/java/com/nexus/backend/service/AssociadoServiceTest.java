package com.nexus.backend.service;

import com.nexus.backend.dto.usuario.UsuarioLoginDto;
import com.nexus.backend.entities.Associado;
import com.nexus.backend.entities.Usuario;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.AssociadoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários da AssociadoService")
class AssociadoServiceTest {

    @Mock // dublê - a nossa "repository" vai ser um dublê
    private AssociadoRepository associadoRepository;

    @InjectMocks
    private AssociadoService associadoService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Dado que, o email não existe, registra o associado com sucesso")
    void registrarAssociadoComSucesso() {
        // GIVEN
        Associado associadoParaRegistrar = new Associado();
        associadoParaRegistrar.setNome("João");
        associadoParaRegistrar.setEmail("joao@email.com");
        associadoParaRegistrar.setSenha("senha123");

        Associado associadoSalvo = new Associado();
        associadoSalvo.setId(1);
        associadoSalvo.setNome("João");
        associadoSalvo.setEmail("joao@email.com");
        associadoSalvo.setSenha("senhaCriptografada"); // Mock retorna isso

        // WHEN
        Mockito.when(associadoRepository.existsByEmail(associadoParaRegistrar.getEmail())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(associadoParaRegistrar.getSenha())).thenReturn("senhaCriptografada");
        Mockito.when(associadoRepository.save(Mockito.any(Associado.class))).thenReturn(associadoSalvo);

        Associado resultado = associadoService.register(associadoParaRegistrar);

        // THEN
        assertEquals(associadoSalvo.getId(), resultado.getId());
        assertEquals(associadoSalvo.getEmail(), resultado.getEmail());
        assertEquals("senhaCriptografada", resultado.getSenha()); // A senha criptografada deve ser igual ao mock

        Mockito.verify(associadoRepository, Mockito.times(1)).existsByEmail(associadoParaRegistrar.getEmail());
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("senha123"); // A senha original que foi passada
        Mockito.verify(associadoRepository, Mockito.times(1)).save(Mockito.any(Associado.class));
    }

    @Test
    @DisplayName("Dado que, o email já existe no sistema, lança exceção")
    void registrarAssociadoComEmailExistente() {
        // GIVEN
        Associado associadoParaRegistrar = new Associado();
        associadoParaRegistrar.setId(1);
        associadoParaRegistrar.setNome("João");
        associadoParaRegistrar.setEmail("joao@email.com");
        associadoParaRegistrar.setSenha("senha123");

        // WHEN
        Mockito.when(associadoRepository.existsByEmail(associadoParaRegistrar.getEmail())).thenReturn(true);

        // THEN
        assertThrows(EntityNotFoundException.class, () -> associadoService.register(associadoParaRegistrar));

        Mockito.verify(associadoRepository, Mockito.times(1)).existsByEmail(associadoParaRegistrar.getEmail());
        Mockito.verify(associadoRepository, Mockito.never()).save(Mockito.any(Associado.class));
    }

    @Test
    @DisplayName("Dado que, o cpf já existe no sistema, lança exceção")
    void registrarAssociadoComCpfExistente() {
        // GIVEN
        Associado associadoParaRegistrar = new Associado();
        associadoParaRegistrar.setId(1);
        associadoParaRegistrar.setNome("João");
        associadoParaRegistrar.setEmail("joao@email.com");
        associadoParaRegistrar.setCpf("123.456.789-00");  // Definindo o CPF
        associadoParaRegistrar.setSenha("senha123");

        // WHEN
        Mockito.when(associadoRepository.existsByCpf(associadoParaRegistrar.getCpf())).thenReturn(true);  // Verifica se o CPF já existe no sistema

        // THEN
        assertThrows(EntityNotFoundException.class, () -> associadoService.register(associadoParaRegistrar));

        Mockito.verify(associadoRepository, Mockito.times(1)).existsByCpf(associadoParaRegistrar.getCpf());  // Verifica se a checagem do CPF foi feita
        Mockito.verify(associadoRepository, Mockito.never()).save(Mockito.any(Associado.class));  // Verifica se o save não foi chamado
    }


    @Test
    @DisplayName("Dado que, o associado existe, atualiza com sucesso")
    void atualizarAssociadoComSucesso() {
        // GIVEN
        Integer id = 1;
        Associado associadoAtualizado = new Associado();
        associadoAtualizado.setNome("João Atualizado");
        associadoAtualizado.setEmail("joao.atualizado@email.com");

        Associado associadoSalvo = new Associado();
        associadoSalvo.setId(id);
        associadoSalvo.setNome("João Atualizado");
        associadoSalvo.setEmail("joao.atualizado@email.com");

        // WHEN
        Mockito.when(associadoRepository.existsById(id)).thenReturn(true);
        Mockito.when(associadoRepository.save(Mockito.any(Associado.class))).thenReturn(associadoSalvo);

        Associado resultado = associadoService.update(id, associadoAtualizado);

        // THEN
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(associadoAtualizado.getNome(), resultado.getNome());
        assertEquals(associadoAtualizado.getEmail(), resultado.getEmail());

        Mockito.verify(associadoRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(associadoRepository, Mockito.times(1)).save(associadoAtualizado);
    }

    @Test
    @DisplayName("Dado que, o associado não existe, lança exceção ao atualizar")
    void atualizarAssociadoNaoExistente() {
        // GIVEN
        Integer id = 1;
        Associado associadoAtualizado = new Associado();
        associadoAtualizado.setNome("João Atualizado");
        associadoAtualizado.setEmail("joao.atualizado@email.com");

        // WHEN
        Mockito.when(associadoRepository.existsById(id)).thenReturn(false);

        // THEN
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> associadoService.update(id, associadoAtualizado));

        assertEquals("Associado não encontrado (a)", exception.getMessage());
        Mockito.verify(associadoRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(associadoRepository, Mockito.never()).save(Mockito.any(Associado.class));
    }

    @Test
    @DisplayName("Dado que, o associado existe, exclui com sucesso")
    void deletarAssociadoComSucesso() {
        // GIVEN
        Integer id = 1;

        // WHEN
        Mockito.when(associadoRepository.existsById(id)).thenReturn(true);

        associadoService.delete(id);

        // THEN
        Mockito.verify(associadoRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(associadoRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Dado que, o associado não existe, lança exceção ao excluir")
    void deletarAssociadoNaoExistente() {
        // GIVEN
        Integer id = 1;

        // WHEN
        Mockito.when(associadoRepository.existsById(id)).thenReturn(false);

        // THEN
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> associadoService.delete(id));

        assertEquals("Associado não encontrado (a)", exception.getMessage());
        Mockito.verify(associadoRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(associadoRepository, Mockito.never()).deleteById(id);
    }
}