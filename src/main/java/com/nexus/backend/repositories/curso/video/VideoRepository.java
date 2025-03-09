package com.nexus.backend.repositories.curso.video;

import com.nexus.backend.entities.curso.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findTop5BycarregadoNoYoutubeFalseOrderByCriadoEmAsc();

    List<Video> findByModuloIdOrderByOrdemAsc(Integer idModulo);
}
