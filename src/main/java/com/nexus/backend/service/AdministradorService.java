package com.nexus.backend.service;

import com.nexus.backend.configuration.security.jwt.GerenciadorTokenJwt;
import com.nexus.backend.dto.usuario.UsuarioLoginDto;
import com.nexus.backend.dto.usuario.UsuarioTokenDto;
import com.nexus.backend.entities.Administrador;
import com.nexus.backend.entities.Usuario;
import com.nexus.backend.entities.curso.video.Video;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.mappers.UsuarioMapper;
import com.nexus.backend.repositories.AdministradorRepository;
import com.nexus.backend.repositories.AssociadoRepository;
import com.nexus.backend.repositories.curso.video.VideoRepository;
import com.nexus.backend.util.IO;
import com.nexus.backend.util.strategy.IOrdenacao;
import com.nexus.backend.util.strategy.QuickSort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministradorService {
    private final AdministradorRepository administradorRepository;
    private final AssociadoRepository associadoRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    private final VideoRepository videoRepository;

//    public List<Video> gerarCsv(){
////        List<Video> videos =  videoRepository.findAll();
////        IOrdenacao<Video> quickSort = new QuickSort<>();
////        Comparator<Video> comparador = Comparator.comparing(Video::getViews);
////        quickSort.ordenar(videos, comparador);
////
////        IO export = new IO();
////        export.export(videos);
////
////        return videoRepository.findAll();
//    }

    public List<Administrador> getAll() {
        return administradorRepository.findAll();
    }

    public Administrador getById(Integer id) {
        return administradorRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Administrador register(Administrador admin) {
        String senhaCript = passwordEncoder.encode(admin.getSenha());
        admin.setSenha(senhaCript);
        admin.setId(null);
        return administradorRepository.save(admin);
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                administradorRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public Administrador update(Integer id, Administrador admin) {
        if (!administradorRepository.existsById(id)) throw new EntityNotFoundException("Associado");

        admin.setId(id);
        String senhaCript = passwordEncoder.encode(admin.getSenha());
        admin.setSenha(senhaCript);
        return administradorRepository.save(admin);
    }

    public void delete(Integer id) {
        if (!administradorRepository.existsById(id)) throw new  EntityNotFoundException("Associado");

        administradorRepository.deleteById(id);
    }
}
