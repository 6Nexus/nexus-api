package com.nexus.backend.repositories.curso.video;

import com.nexus.backend.entities.curso.video.ProgressoVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgressoVideoRepository extends JpaRepository<ProgressoVideo, Integer> {
    Optional<ProgressoVideo> findByMatriculaIdAndVideoId(Integer matriculaId, Integer videoId);
}
