package com.nexus.backend.service.curso;

import com.nexus.backend.entities.curso.Curso;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.CursoRepository;
import com.nexus.backend.service.ProfessorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {
    private final CursoRepository cursoRepository;
    private final ProfessorService professorService;
    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    public List<Curso> listarPorCategoria(String categoria) {
        return cursoRepository.findAllByCategoria(categoria);
    }

    public List<Curso> listarPorProfessor(Integer professorId) {
        return cursoRepository.findAllByProfessorId(professorId);
    }

    public List<Curso> listarPorIds(List<Integer> idsCursos) {
        return cursoRepository.findAllByIdIn(idsCursos);
    }

    public Integer cadastrar(Curso curso, Integer idProfessor) {
        curso.setProfessor(professorService.getById(idProfessor));
        return cursoRepository.save(curso).getId();
    }

    @Transactional
    public String cadastrarCapa(Integer cursoId, MultipartFile capa) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new EntityNotFoundException("Curso");
        }

        try{
            Curso cursoEncontrado = buscarPorId(cursoId);

            String capaName = generateStoredName(cursoEncontrado.getId(), cursoEncontrado.getTitulo());
            String capaUrl = getFileUrl(capaName);

            deleteS3File(capaName);

            var request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(capaName)
                    .contentType(capa.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(capa.getBytes()));

            cursoEncontrado.setCapaUrl(getFileUrl(capaUrl));
            cursoRepository.save(cursoEncontrado);

            return capaUrl;
        }catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload para o S3", e);
        }
    }

    private void deleteS3File(String fileName) {
        try {
            var deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteRequest);
        } catch (Exception e) {
            System.out.println("Aviso: Falha ao deletar a capa antiga do S3. Detalhes: " + e.getMessage());
        }
    }

    private String generateStoredName(Integer cursoId, String nomeCurso) {
        return cursoId + "_" + nomeCurso + "_" + "capa";
    }
    private String getFileUrl(String fileName) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);
    }

    public String buscarCapaPorCursoId(Integer cursoId) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new EntityNotFoundException("Curso");
        }

        return buscarPorId(cursoId).getCapaUrl();
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

    public Curso atualizar(int id, int idProf, Curso entity) {
        if (!cursoRepository.existsById(id)) throw  new EntityNotFoundException("Curso");
        String capaUrl = buscarCapaPorCursoId(id);

        entity.setCapaUrl(capaUrl);
        entity.setProfessor(professorService.getById(idProf));
        entity.setId(id);
        return cursoRepository.save(entity);
    }
}
