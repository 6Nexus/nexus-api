package com.nexus.backend.service.curso;

import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.CursoRepository;
import com.nexus.backend.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {
    private final CursoRepository cursoRepository;
    private final ProfessorService professorService;

    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    public List<Curso> listarPorCategoria(String categoria) {
        return cursoRepository.findAllByCategoria(categoria);
    }

    public List<Curso> listarPorProfessor(Integer professorId) {
        return cursoRepository.findAllByProfessorId(professorId);
    }

    public Integer cadastrar(Curso curso, Integer idProfessor) {
        curso.setProfessor(professorService.getById(idProfessor));
        return cursoRepository.save(curso).getId();
    }

    public Curso buscarPorId(Integer idCurso) {
        return cursoRepository.findById(idCurso).orElseThrow(
                () -> new EntityNotFoundException("Curso")
        );
    }

    public String alterarTitulo(Integer idCurso, String novoTitulo){
        Curso curso = buscarPorId(idCurso);
        curso.setTitulo(novoTitulo);

        return cursoRepository.save(curso).getTitulo();
    }

    public String alterarCategoria(Integer idCurso, String novaCatetegoria){
        Curso curso = buscarPorId(idCurso);
        curso.setCategoria(novaCatetegoria);

        return cursoRepository.save(curso).getCategoria();
    }

    public String alterarDescricao(Integer idCurso, String novaDescricao){
        Curso curso = buscarPorId(idCurso);
        curso.setDescricao(novaDescricao);

        return cursoRepository.save(curso).getDescricao();
    }

    public void deletar(Integer idCurso) {
        if (!cursoRepository.existsById(idCurso)) throw new EntityNotFoundException("Curso");

        cursoRepository.deleteById(idCurso);
    }
}
