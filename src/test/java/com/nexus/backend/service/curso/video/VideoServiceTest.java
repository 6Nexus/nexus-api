package com.nexus.backend.service.curso.video;

import com.nexus.backend.entities.curso.video.Video;
import com.nexus.backend.exceptions.EntityNotFoundException;
import com.nexus.backend.repositories.curso.video.VideoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários da VideoService")
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    @Test
    @DisplayName("Dado que, o módulo existe, lista os vídeos por módulo com sucesso")
    void listarVideosPorModuloComSucesso() {
        // GIVEN
        Integer idModulo = 1;
        List<Video> videosEsperados = Arrays.asList(new Video(), new Video());

        when(videoRepository.findByModuloIdOrderByOrdemAsc(idModulo)).thenReturn(videosEsperados);

        // WHEN
        List<Video> resultado = videoService.listarPorModulo(idModulo);

        // THEN
        assertNotNull(resultado);
        assertEquals(videosEsperados.size(), resultado.size());
        verify(videoRepository, times(1)).findByModuloIdOrderByOrdemAsc(idModulo);
    }

    @Test
    @DisplayName("Dado que, o vídeo não existe, lança exceção ao deletar vídeo inexistente")
    void deletarVideoInexistente() {
        // GIVEN
        Integer idVideo = 1;
        when(videoRepository.existsById(idVideo)).thenReturn(false);

        // THEN
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> videoService.deletar(idVideo));
        assertEquals("Vídeo não encontrado (a)", exception.getMessage());

        verify(videoRepository, times(1)).existsById(idVideo);
        verify(videoRepository, never()).deleteById(idVideo);
    }

    @Test
    @DisplayName("Dado que, o vídeo existe, deleta o vídeo com sucesso")
    void deletarVideoExistenteComSucesso() {
        // GIVEN
        Integer idVideo = 1;
        when(videoRepository.existsById(idVideo)).thenReturn(true);

        // WHEN
        videoService.deletar(idVideo);

        // THEN
        verify(videoRepository, times(1)).existsById(idVideo);
        verify(videoRepository, times(1)).deleteById(idVideo);
    }

    @Test
    @DisplayName("Dado que, o vídeo não existe, não deleta e lança exceção")
    void naoDeletarVideoInexistente() {
        // GIVEN
        Integer idVideo = 1;

        when(videoRepository.existsById(idVideo)).thenReturn(false);

        // WHEN & THEN
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> videoService.deletar(idVideo));
        assertEquals("Vídeo não encontrado (a)", exception.getMessage());

        verify(videoRepository, times(1)).existsById(idVideo);
        verify(videoRepository, times(0)).deleteById(idVideo);
    }


}
