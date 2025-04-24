package com.nexus.backend.service;

import com.nexus.backend.configuration.security.jwt.GerenciadorTokenJwt;
import com.nexus.backend.dto.usuario.UsuarioLoginDto;
import com.nexus.backend.dto.usuario.UsuarioTokenDto;
import com.nexus.backend.entities.Associado;
import com.nexus.backend.entities.Professor;
import com.nexus.backend.entities.Usuario;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.mappers.UsuarioMapper;
import com.nexus.backend.repositories.AssociadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssociadoService {
    private final AssociadoRepository associadoRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;

    public List<Associado> getAll() {
        return associadoRepository.findAll();
    }

    public Associado getById(Integer id) {
        return associadoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Associado setAprovado(Integer id){
        if (!associadoRepository.existsById(id)) throw new EntityNotFoundException("Associado");

        Associado a = getById(id);
        a.setId(id);
        a.setAprovado(true);
        return associadoRepository.save(a);
    }


    public Associado bloquear(Integer id){
        if (!associadoRepository.existsById(id)) throw new EntityNotFoundException("Professor");

        Associado a = getById(id);
        a.setId(id);
        a.setAprovado(false);
        return associadoRepository.save(a);
    }

//    public List<Associado>

    public Associado register(Associado a) {
        String senhaCript = passwordEncoder.encode(a.getSenha());
        a.setSenha(senhaCript);
        a.setId(null);
        return associadoRepository.save(a);
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                associadoRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    @Transactional
    public Associado update(Integer id, Associado a) {
        if (!associadoRepository.existsById(id)) throw new EntityNotFoundException("Associado");

        a.setId(id);
        return associadoRepository.save(a);
    }

    public void adminDelete(Integer id) {
        if (!associadoRepository.existsById(id)) throw new EntityNotFoundException("Associado");

        associadoRepository.deleteById(id);
    }

    public void delete(Integer id, UsuarioLoginDto usuarioLoginDto) {
        if (!associadoRepository.existsById(id)) throw new EntityNotFoundException("Associado");

        UsuarioTokenDto usuarioAutenticado = autenticar(usuarioLoginDto);
        if (!(usuarioAutenticado.getUserId().equals(id))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        associadoRepository.deleteById(id);
    }

    public List<Associado> getAprovadoFalse() {
        return associadoRepository.findByAprovadoFalse();
    }

    public List<Associado> getAprovadoTrue() {
        return associadoRepository.findByAprovadoTrue();
    }
}
