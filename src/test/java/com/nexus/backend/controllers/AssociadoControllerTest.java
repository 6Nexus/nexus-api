package com.nexus.backend.controllers;

import com.nexus.backend.dto.associado.AssociadoCriacaoDto;
import com.nexus.backend.dto.associado.AssociadoRespostaDto;
import com.nexus.backend.dto.usuario.UsuarioLoginDto;
import com.nexus.backend.dto.usuario.UsuarioTokenDto;
import com.nexus.backend.entities.Associado;
import com.nexus.backend.enums.TipoUsuario;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.mappers.AssociadoMapper;
import com.nexus.backend.service.AssociadoService;
import io.micrometer.core.instrument.config.validate.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários da AssociadoController")
class AssociadoControllerTest {

    @Mock
    private AssociadoService associadoService;

    @Mock
    private AssociadoMapper associadoMapper;

    @InjectMocks
    private AssociadoController associadoController;

    private UsuarioLoginDto usuarioLoginDto;
    private UsuarioTokenDto usuarioTokenDto;


    @Test
    @DisplayName("Dado que, ao chamar o login, retorna um token de autenticação com status 200")
    void login_DeveRetornarToken_ComStatus200() {
        // Mockando os dados de entrada
        UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto("usuario@example.com", "senha123");

        // Mockando o retorno do serviço de autenticação
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto("usuario@example.com", "tokenGerado123");
        when(associadoService.autenticar(any(UsuarioLoginDto.class))).thenReturn(usuarioTokenDto);

        // Chamando o endpoint do controller
        ResponseEntity<UsuarioTokenDto> resultado = associadoController.login(usuarioLoginDto);

        // Validando a resposta
        assertEquals(HttpStatus.OK, resultado.getStatusCode()); // Verifica se o status é 200
        assertNotNull(resultado.getBody()); // Verifica se o corpo não é nulo
        assertEquals(usuarioTokenDto.getEmail(), resultado.getBody().getEmail()); // Verifica se o e-mail está correto
        assertEquals(usuarioTokenDto.getToken(), resultado.getBody().getToken()); // Verifica se o token está correto
    }


    @Test
    @DisplayName("Dado que, ao chamar o login com dados nulos, retorna status 400 com mensagem de erro")
    void login_DeveRetornarStatus400_QuandoDadosNull() {
        // GIVEN
        UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto(null, null);

        // Mockando os serviços para lançar uma exceção no caso de falha na autenticação
        when(associadoService.autenticar(usuarioLoginDto))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ou senha não podem ser nulos"));

        // WHEN & THEN
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            associadoController.login(usuarioLoginDto);
        });

        // Verifica se a exceção tem o status correto
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Email ou senha não podem ser nulos", exception.getReason());
    }


    @Test
    @DisplayName("Dado que, ao chamar o login com dados inválidos, retorna status 400 com mensagem de erro")
    void login_DeveRetornarStatus400_QuandoDadosInvalidos() {
        // GIVEN
        UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto("user@example.com", "senhaErrada");

        // Mockando os serviços para lançar uma exceção no caso de falha na autenticação
        when(associadoService.autenticar(usuarioLoginDto))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ou senha inválidos"));

        // WHEN & THEN
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            associadoController.login(usuarioLoginDto);
        });

        // Verifica se a exceção tem o status correto
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Email ou senha inválidos", exception.getReason());
    }

    @Test
    @DisplayName("Dado que, ao cadastrar um novo associado, ele é retornado corretamente")
    void cadastrarAssociadoDeveRetornarAssociado_QuandoDadosValidos() {

        // Dado (GIVEN) - Usando o builder do Lombok para criar o DTO com dados válidos
        AssociadoCriacaoDto associadoCriacaoDto = AssociadoCriacaoDto.builder()
                .nome("João")
                .email("joao@email.com")
                .senha("senha123")
                .cpf("123456789")
                .telefone("987654321")
                .tipoUsuario(TipoUsuario.ASSOCIADO)
                .endereco("Rua X")
                .grauParentescoComDesaparecido("Parentesco com Desaparecido")
                .build();

        // Criando o objeto associado esperado (simulando o que o serviço irá retornar)
        Associado associado = new Associado();
        associado.setId(1);
        associado.setNome("João");
        associado.setEmail("joao@email.com");
        associado.setEndereco("Rua X");
        associado.setTelefone("987654321");
        associado.setGrauParentescoComDesaparecido("Parentesco com Desaparecido");

        // Criando o DTO de resposta esperado
        AssociadoRespostaDto associadoRespostaDto = AssociadoRespostaDto.builder()
                .id(1)
                .email("joao@email.com")
                .nome("João")
                .endereco("Rua X")
                .telefone("987654321")
                .grauParentescoComDesaparecido("Parentesco com Desaparecido")
                .build();

        // Mockando o comportamento do Mapper para simular a conversão do DTO
        when(associadoMapper.toCriacaoEntity(any(AssociadoCriacaoDto.class))).thenReturn(associado);

        // Mockando o serviço para retornar o associado criado
        when(associadoService.register(any(Associado.class))).thenReturn(associado);

        // Mockando o comportamento do Mapper para retornar o DTO de resposta
        when(associadoMapper.toRespostaDto(any(Associado.class))).thenReturn(associadoRespostaDto);

        // Quando (WHEN)
        ResponseEntity<AssociadoRespostaDto> response = associadoController.cadastrarAssociado(associadoCriacaoDto);

        // Então (THEN)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "O corpo da resposta não pode ser nulo");

        // Asserções para verificar os dados
        assertEquals("João", response.getBody().getNome());
        assertEquals("joao@email.com", response.getBody().getEmail());
        assertEquals("Rua X", response.getBody().getEndereco());
        assertEquals("987654321", response.getBody().getTelefone());
        assertEquals("Parentesco com Desaparecido", response.getBody().getGrauParentescoComDesaparecido());
    }

    @Test
    @DisplayName("Dado que, ao atualizar um associado com dados válidos, a resposta retorna com status 201 CREATED e dados atualizados")
    void atualizarAssociado_DeveRetornarCriado_QuandoDadosValidos() {
        // Dado (GIVEN) - Usando o builder do Lombok para criar o DTO com dados válidos
        AssociadoCriacaoDto associadoCriacaoDto = AssociadoCriacaoDto.builder()
                .nome("João Atualizado")
                .email("joaoatualizado@email.com")
                .senha("senha1234")
                .cpf("12345678901")
                .telefone("987654321")
                .tipoUsuario(TipoUsuario.ASSOCIADO)
                .endereco("Rua Y")
                .grauParentescoComDesaparecido("Novo grau de parentesco")
                .build();

        // Criando o objeto associado simulado para o mock
        Associado associado = new Associado();
        associado.setId(1);
        associado.setNome("João Atualizado");
        associado.setEmail("joaoatualizado@email.com");
        associado.setEndereco("Rua Y");

        // Criando o DTO de resposta esperado
        AssociadoRespostaDto associadoRespostaDto = new AssociadoRespostaDto(1, "João Atualizado", "joaoatualizado@email.com", "987654321", "Rua Y", "Novo grau de parentesco");

        // Mockando o comportamento do Mapper para simular a conversão do DTO
        when(associadoMapper.toCriacaoEntity(any(AssociadoCriacaoDto.class))).thenReturn(associado);

        // Mockando o serviço para retornar o associado atualizado
        when(associadoService.update(eq(1), any(Associado.class))).thenReturn(associado);

        // Mockando o comportamento do Mapper para retornar o DTO de resposta
        when(associadoMapper.toRespostaDto(any(Associado.class))).thenReturn(associadoRespostaDto);

        // Quando (WHEN) - Realizando o request para o controlador
        ResponseEntity<AssociadoRespostaDto> response = associadoController.atualizarAssociado(1, associadoCriacaoDto);

        // Então (THEN) - Verificando se o código de status da resposta é 201 (CREATED)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "O corpo da resposta não pode ser nulo");
        assertEquals("João Atualizado", response.getBody().getNome());
        assertEquals("joaoatualizado@email.com", response.getBody().getEmail());
        assertEquals("Rua Y", response.getBody().getEndereco());
        assertEquals("987654321", response.getBody().getTelefone());
        assertEquals("Novo grau de parentesco", response.getBody().getGrauParentescoComDesaparecido());
    }

}
