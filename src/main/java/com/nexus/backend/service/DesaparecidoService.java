package com.nexus.backend.service;

import com.nexus.backend.entities.Desaparecido;
import com.nexus.backend.entities.Professor;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.DesaparecidoRepository;
import com.nexus.backend.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DesaparecidoService {
    private final DesaparecidoRepository desaparecidoRepository;

    public List<Desaparecido> getAll() {
        return desaparecidoRepository.findAll();
    }

    public Desaparecido getById(Integer id) {
        return desaparecidoRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Desaparecido> getByDataOcorrencia(LocalDate inicio, LocalDate fim){
        return desaparecidoRepository.findByDataOcorrenciaBetween(inicio, fim);
    }

    public List<Desaparecido> getByNome(String nome){
        return desaparecidoRepository.findByNomeContainsIgnoreCase(nome);
    }
    public Desaparecido register(Desaparecido d) {
        d.setId(null);
        return desaparecidoRepository.save(d);
    }

    public Desaparecido update(Integer id, Desaparecido d) {
        if (!desaparecidoRepository.existsById(id)) throw new EntityNotFoundException("Desaparecido");

        d.setId(id);
        return desaparecidoRepository.save(d);
    }

    public void delete(Integer id) {
        if (!desaparecidoRepository.existsById(id)) throw new  EntityNotFoundException("Desaparecido");

        desaparecidoRepository.deleteById(id);
    }

}
