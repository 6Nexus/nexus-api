package com.nexus.backend.strategy;

import java.util.Comparator;
import java.util.List;

public interface IOrdenacao<T> {
    void ordenar(List<T> lista, Comparator<T> comparator);
    void ordenarDesc(List<T> lista, Comparator<T> comparator);
}