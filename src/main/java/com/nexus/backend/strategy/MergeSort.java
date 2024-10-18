package com.nexus.backend.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort<T> implements IOrdenacao<T>{

    @Override
    public void ordenar(List<T> lista, Comparator<T> comparador) {
        mergeSort(lista, 0, lista.size() - 1, comparador, false);
    }

    // Método para ordenação decrescente
    @Override
    public void ordenarDesc(List<T> lista, Comparator<T> comparador) {
        mergeSort(lista, 0, lista.size() - 1, comparador, true);
    }

    // Método principal do MergeSort, ajustado para aceitar a flag 'decrescente'
    private void mergeSort(List<T> lista, int inicio, int fim, Comparator<T> comparador, boolean decrescente) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSort(lista, inicio, meio, comparador, decrescente);
            mergeSort(lista, meio + 1, fim, comparador, decrescente);
            intercalar(lista, inicio, meio, fim, comparador, decrescente);
        }
    }

    // Método de intercalação ajustado para lidar com a ordenação decrescente
    private void intercalar(List<T> lista, int inicio, int meio, int fim, Comparator<T> comparador, boolean decrescente) {
        List<T> esquerda = new ArrayList<>(lista.subList(inicio, meio + 1));
        List<T> direita = new ArrayList<>(lista.subList(meio + 1, fim + 1));

        int i = 0, j = 0;
        int k = inicio;

        // Comparação entre os elementos das listas esquerda e direita
        while (i < esquerda.size() && j < direita.size()) {
            if ((decrescente && comparador.compare(esquerda.get(i), direita.get(j)) > 0) ||
                    (!decrescente && comparador.compare(esquerda.get(i), direita.get(j)) < 0)) {
                lista.set(k, esquerda.get(i));
                i++;
            } else {
                lista.set(k, direita.get(j));
                j++;
            }
            k++;
        }
        // Adiciona os elementos restantes da lista esquerda, se houver
        while (i < esquerda.size()) {
            lista.set(k, esquerda.get(i));
            i++;
            k++;
        }
        // Adiciona os elementos restantes da lista direita, se houver
        while (j < direita.size()) {
            lista.set(k, direita.get(j));
            j++;
            k++;
        }
    }
}

