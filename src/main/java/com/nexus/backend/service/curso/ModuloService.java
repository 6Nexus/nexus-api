package com.nexus.backend.service.curso;

import com.nexus.backend.entities.curso.Modulo;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.ModuloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuloService {
    private final ModuloRepository moduloRepository;
    private final CursoService cursoService;

    public List<Modulo> listarPorCurso(Integer cursoId) {
        return moduloRepository.findAllByCursoIdOrderByOrdemAsc(cursoId);
    }

    public Integer cadastrar(Modulo modulo, Integer cursoId) {
        modulo.setCriadoEm(LocalDateTime.now());
        modulo.setCurso(cursoService.buscarPorId(cursoId));
        return moduloRepository.save(modulo).getId();
    }

    public Modulo buscarPorId(Integer idModulo) {
        return moduloRepository.findById(idModulo).orElseThrow(
                () -> new EntityNotFoundException("Módulo")
        );
    }

    public void alterarOrdem(Integer idModulo01, Integer idModulo02){
        Modulo modulo01 = buscarPorId(idModulo01);
        Modulo modulo02 = buscarPorId(idModulo02);

        Integer ordemModulo01 = modulo01.getOrdem();
        modulo01.setOrdem(modulo02.getOrdem());
        modulo02.setOrdem(ordemModulo01);

        moduloRepository.save(modulo01);
        moduloRepository.save(modulo02);
    }

    public Integer qtdModulosPorCurso(Integer idCurso) {
        return moduloRepository.countByCursoId(idCurso).orElseThrow(
                () -> new EntityNotFoundException("Módulo")
        );
    }

    public void deletar(Integer idModulo) {
        if (!moduloRepository.existsById(idModulo)) throw new EntityNotFoundException("Módulo");

        moduloRepository.deleteById(idModulo);
    }

    public Modulo atualizar(int id, int idCurso, Modulo entity) {
        if(!moduloRepository.existsById(id)) throw new EntityNotFoundException("Modulo");
        entity.setCurso(cursoService.buscarPorId(idCurso));
        entity.setCriadoEm(LocalDateTime.now());
        entity.setId(id);
        return moduloRepository.save(entity);
    }
}
