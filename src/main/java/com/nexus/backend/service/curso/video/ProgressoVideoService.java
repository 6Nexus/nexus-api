package com.nexus.backend.service.curso.video;

import com.nexus.backend.entities.curso.questionario.ProgressoQuestionario;
import com.nexus.backend.entities.curso.video.ProgressoVideo;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.questionario.ProgressoQuestionarioRepository;
import com.nexus.backend.repositories.curso.video.ProgressoVideoRepository;
import com.nexus.backend.service.curso.MatriculaService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgressoVideoService {
    private final ProgressoVideoRepository progressoVideoRepository;
    private final MatriculaService matriculaService;
    private final VideoService videoService;

    public Integer cadastrar(ProgressoVideo progressoEntrada, @Positive @NotNull Integer matriculaId, @Positive @NotNull Integer videoId) {
        progressoEntrada.setMatricula(matriculaService.buscarPorId(matriculaId));
        progressoEntrada.setVideo(videoService.buscarPorId(videoId));
        return progressoVideoRepository.save(progressoEntrada).getId();
    }

    public ProgressoVideo buscarPorMatriculaEVideo(Integer matriculaId, Integer videoId) {
        return progressoVideoRepository.findByMatriculaIdAndVideoId(matriculaId, videoId).orElseThrow(
                () -> new EntityNotFoundException("Progresso Vídeo")
        );
    }

    public ProgressoVideo buscarPorId(Integer progressoId) {
        return progressoVideoRepository.findById(progressoId).orElseThrow(
                () -> new EntityNotFoundException("Progresso Vídeo")
        );
    }

    public ProgressoVideo atualizarAssistido(Integer progressoId) {
        ProgressoVideo progressoEncontrado = buscarPorId(progressoId);
        progressoEncontrado.setAssistido(!progressoEncontrado.getAssistido());
        return progressoVideoRepository.save(progressoEncontrado);
    }
}
