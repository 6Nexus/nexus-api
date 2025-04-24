package com.nexus.backend.service;

import com.nexus.backend.dto.usuario.UsuarioDetalhesDto;
import com.nexus.backend.entities.Professor;
import com.nexus.backend.entities.Usuario;
import com.nexus.backend.repositories.AdministradorRepository;
import com.nexus.backend.repositories.AssociadoRepository;
//import com.nexus.backend.repositories.DesaparecidoRepository;
import com.nexus.backend.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private AdministradorRepository administradorRepository;


    // Método da interface implementada
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuarioOpt = associadoRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()){
            usuarioOpt = professorRepository.findByEmail(username);
        }

        if (usuarioOpt.isEmpty()){
            usuarioOpt = administradorRepository.findByEmail(username);
        }


        if (usuarioOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }

        return new UsuarioDetalhesDto(usuarioOpt.get());
    }
}
