package com.nexus.backend.service;

import com.nexus.backend.entities.Professor;
import com.nexus.backend.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> getAll() {
        return professorRepository.findAll();
    }

    public Professor getById(Integer id) {
        return professorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Professor register(Professor p) {
        p.setId(null);
        return professorRepository.save(p);
    }

    public Professor update(Integer id, Professor p) {
        if (!professorRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        p.setId(id);
        return professorRepository.save(p);
    }

    public void delete(Integer id) {
        if (!professorRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        professorRepository.deleteById(id);
    }

}
