package com.nexus.backend.service;

import com.nexus.backend.entities.Associado;
import com.nexus.backend.entities.Desaparecido;
import com.nexus.backend.repositories.AssociadoRepository;
import com.nexus.backend.repositories.DesaparecidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssociadoService {
    private final AssociadoRepository associadoRepository;

    public List<Associado> getAll() {
        return associadoRepository.findAll();
    }

    public Associado getById(Integer id) {
        return associadoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Associado register(Associado a) {
        a.setId(null);
        return associadoRepository.save(a);
    }

    public Associado update(Integer id, Associado a) {
        if (!associadoRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        a.setId(id);
        return associadoRepository.save(a);
    }

    public void delete(Integer id) {
        if (!associadoRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        associadoRepository.deleteById(id);
    }

}
