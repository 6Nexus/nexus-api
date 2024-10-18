package com.nexus.backend.controllers;

import com.nexus.backend.strategy.IOrdenacao;
import com.nexus.backend.strategy.MergeSort;
import com.nexus.backend.strategy.QuickSort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/strategy")
@RequiredArgsConstructor
public class TesteStrategyController {
    public static class Musica {
        private String titulo;
        private String artista;
        private int anoLancamento;

        public Musica(String titulo, String artista, int anoLancamento) {
            this.titulo = titulo;
            this.artista = artista;
            this.anoLancamento = anoLancamento;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getArtista() {
            return artista;
        }

        public int getAnoLancamento() {
            return anoLancamento;
        }

        @Override
        public String toString() {
            return "Musica{" +
                    "titulo='" + titulo + '\'' +
                    ", artista='" + artista + '\'' +
                    ", anoLancamento=" + anoLancamento +
                    '}';
        }
    }


    // Lista de músicas de exemplo (em um cenário real, você buscaria isso de um banco de dados ou repositório)
    private List<Musica> getMusicas() {
        return Arrays.asList(
                new Musica("Song A", "Artist A", 2020),
                new Musica("Song B", "Artist B", 2019),
                new Musica("Song C", "Artist C", 2021)
        );
    }

    @GetMapping("/ordenarQuick/{criterio}")
    public ResponseEntity<List<Musica>> ordenarQuick(@PathVariable String criterio) {
        // Obtém a lista de músicas
        List<Musica> musicas = getMusicas();
        // Seleciona a estratégia de ordenação com base no parâmetro 'tipoOrdenacao'
        IOrdenacao<Musica> estrategia = new QuickSort<>();
        // Define o comparador para o critério desejado (ex: ordenar por 'anoLancamento' ou 'titulo')
        Comparator<Musica> comparador;
        switch (criterio.toLowerCase()) {
            case "anolancamento":
                comparador = Comparator.comparing(Musica::getAnoLancamento);
                break;
            case "titulo":
                comparador = Comparator.comparing(Musica::getTitulo);
                break;
            default:
                throw new IllegalArgumentException("Critério de ordenação não suportado: " + criterio);
        }
        // Aplica a estratégia de ordenação escolhida
        estrategia.ordenar(musicas, comparador);
        // Retorna a lista de músicas ordenada
        return ResponseEntity.ok(musicas);
    }

    @GetMapping("/ordenarMergeDesc/{criterio}")
    public ResponseEntity<List<Musica>> ordenarMerge(@PathVariable String criterio) {
        // Obtém a lista de músicas
        List<Musica> musicas = getMusicas();
        // Seleciona a estratégia de ordenação com base no parâmetro 'tipoOrdenacao'
        IOrdenacao<Musica> estrategia = new MergeSort<>();
        // Define o comparador para o critério desejado (ex: ordenar por 'anoLancamento' ou 'titulo')
        Comparator<Musica> comparador;
        switch (criterio.toLowerCase()) {
            case "anolancamento":
                comparador = Comparator.comparing(Musica::getAnoLancamento);
                break;
            case "titulo":
                comparador = Comparator.comparing(Musica::getTitulo);
                break;
            default:
                throw new IllegalArgumentException("Critério de ordenação não suportado: " + criterio);
        }
        // Aplica a estratégia de ordenação escolhida
        estrategia.ordenarDesc(musicas, comparador);
        // Retorna a lista de músicas ordenada
        return ResponseEntity.ok(musicas);
    }
}
