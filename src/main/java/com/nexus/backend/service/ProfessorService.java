package com.nexus.backend.service;

import com.nexus.backend.configuration.security.jwt.GerenciadorTokenJwt;
import com.nexus.backend.dto.usuario.UsuarioLoginDto;
import com.nexus.backend.dto.usuario.UsuarioTokenDto;
import com.nexus.backend.entities.Professor;
import com.nexus.backend.entities.Usuario;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.mappers.UsuarioMapper;
import com.nexus.backend.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.sound.sampled.Port;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;

    public List<Professor> getAprovadoFalse(){
        return professorRepository.findByAprovadoFalse();
    }
    public List<Professor> getAprovadoTrue(){
        return professorRepository.findByAprovadoTrue();
    }
    public List<Professor> getAll() {
        return professorRepository.findAll();
    }

    public Professor setAprovado(Integer id){
        if (!professorRepository.existsById(id)) throw new EntityNotFoundException("Professor");

        Professor p = getById(id);
        p.setId(id);
        p.setAprovado(true);
        return professorRepository.save(p);
    }

    public Professor bloquear(Integer id){
        if (!professorRepository.existsById(id)) throw new EntityNotFoundException("Professor");

        Professor p = getById(id);
        p.setId(id);
        p.setAprovado(false);
        return professorRepository.save(p);
    }


    public Professor getById(Integer id) {
        return professorRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                professorRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public Professor register(Professor p) {
        String senhaCript = passwordEncoder.encode(p.getSenha());
        p.setSenha(senhaCript);
        p.setId(null);
        return professorRepository.save(p);
    }

    public Professor update(Integer id, Professor p) {
        if (!professorRepository.existsById(id)) throw new EntityNotFoundException("Professor");

        p.setId(id);
        String senhaCript = passwordEncoder.encode(p.getSenha());
        p.setSenha(senhaCript);
        return professorRepository.save(p);
    }

    public void delete(Integer id) {
        if (!professorRepository.existsById(id)) throw new EntityNotFoundException("Professor");

        professorRepository.deleteById(id);
    }

}
