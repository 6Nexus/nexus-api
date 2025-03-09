package com.nexus.backend.util.strategy;

import java.util.Comparator;
import java.util.List;

public class QuickSort<T> implements IOrdenacao<T> {

    @Override
    public void ordenar(List<T> lista, Comparator<T> comparador) {
        quickSort(lista, 0, lista.size() - 1, comparador, false);
    }

    // Método para ordenação decrescente
    @Override
    public void ordenarDesc(List<T> lista, Comparator<T> comparador) {
        quickSort(lista, 0, lista.size() - 1, comparador, true);
    }

    // Método principal do QuickSort, ajustado para aceitar a flag 'decrescente'
    private void quickSort(List<T> lista, int inicio, int fim, Comparator<T> comparador, boolean decrescente) {
        if (inicio < fim) {
            int indicePivo = particionar(lista, inicio, fim, comparador, decrescente);
            quickSort(lista, inicio, indicePivo - 1, comparador, decrescente);
            quickSort(lista, indicePivo + 1, fim, comparador, decrescente);
        }
    }

    // Método de particionamento ajustado para lidar com a ordenação decrescente
    private int particionar(List<T> lista, int inicio, int fim, Comparator<T> comparador, boolean decrescente) {
        T pivo = lista.get(fim);
        int indiceMenor = inicio - 1;
        for (int i = inicio; i < fim; i++) {
            // Se for decrescente, invertemos a comparação
            if ((decrescente && comparador.compare(lista.get(i), pivo) > 0) ||
                    (!decrescente && comparador.compare(lista.get(i), pivo) < 0)) {
                indiceMenor++;
                T temporario = lista.get(indiceMenor);
                lista.set(indiceMenor, lista.get(i));
                lista.set(i, temporario);
            }
        }
        T temporario = lista.get(indiceMenor + 1);
        lista.set(indiceMenor + 1, lista.get(fim));
        lista.set(fim, temporario);
        return indiceMenor + 1;
    }
}
