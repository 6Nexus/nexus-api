package com.nexus.backend.util;

public class PilhaObj<T> {
    private T[] pilha;
    private int topo;

    public PilhaObj(int capacidade) {
        this.pilha = (T[]) new Object[capacidade];
        this.topo = -1;
    }

    public Boolean isEmpty() {
        return topo == -1;
    }

    public Boolean isFull() {
        return topo == pilha.length - 1;
    }

    public void push(T info) {
        if(isFull()) throw new IllegalStateException();
        pilha[++topo] = info;
    }

    public T pop() {
        if(isEmpty()) throw new IllegalStateException();
        T valor = pilha[topo];
        topo--;
        return valor;
    }

    public T peek() {
        if(isEmpty()) return null;
        return pilha[topo];
    }

    public void exibe() {
        for (int i = topo; i >= 0; i--) {
            System.out.print(pilha[i] + "  ");
        }
    }

    public int getTopo() {
        return topo;
    }
}