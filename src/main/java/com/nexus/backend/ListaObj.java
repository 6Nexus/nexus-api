package com.nexus.backend;

public class ListaObj<T>{

    private T[] vetor;
    private int nroElem;


    public ListaObj(int tam) {
        this.vetor = (T[]) new Object[tam];
        this.nroElem = 0;
    }


    public void adiciona(T elemento){
        if(nroElem == vetor.length){
            throw new IllegalStateException("Lista cheia");
        }else{
            vetor[nroElem++] = elemento;
        }
    }


    public int busca(T elementoProcurado){
        for (int i = 0; i < nroElem; i++) {
            if(vetor[i].equals(elementoProcurado)) {
                return i;
            }
        }
        return -1;
    }


    public boolean removePeloIndice(int indice){
        if(indice < 0 || indice > nroElem-1) {
            return false;
        }
        for (int i = indice; i < nroElem - 1; i++) {
            vetor[i] = vetor[i + 1];
        }
        vetor[nroElem - 1] = null;
        nroElem--;
        return true;
    }


    public boolean removeElemento(T elemento){
        return removePeloIndice(busca(elemento));
    }


    public void exibe(){
        for (int i = 0; i < nroElem; i++) {
            System.out.printf(vetor[i] + " ");
        }
    }


    public boolean substitui(T valorAntigo, T valorNovo){
        boolean foiTrocado = false;
        for (int i = 0; i < nroElem; i++) {
            if(valorAntigo == vetor[i]){
                vetor[i] = valorNovo;
                foiTrocado = true;
            }
        }
        return foiTrocado;
    }


    public int contaOcorrencias(T elemento){
        int qtdElementoNoVetor = 0;
        for (int i = 0; i < nroElem; i++) {
            if(elemento == vetor[i]){
                qtdElementoNoVetor++;
            }
        }

        return qtdElementoNoVetor;
    }

    public boolean adicionaNoInicio(T elementoAdicionado){
        if(nroElem == vetor.length-1){
            throw new IllegalStateException("Lista cheia");
        }

        for (int i = nroElem-1; i >= 0 ; i--) {
            vetor[i+1] = vetor[i];
        }
        vetor[0] = elementoAdicionado;
        nroElem++;
        return true;

    }

    public void limpa(){
        for (int i = 0; i < nroElem; i++) {
            vetor[i] = null;
        }
        nroElem = 0;
    }



    public T getElemento(int indice) {
        if(indice < 0  || indice >= nroElem){
            return null;
        }
        return vetor[indice];
    }

    public void setElemento(int indice, T valor) {
        if (indice < 0 || indice >= nroElem) {
            throw new IndexOutOfBoundsException("Índice fora dos limites: " + indice);
        }
        vetor[indice] = valor;
    }

    public int getTamanho() {
        return nroElem;
    }

    public T[] getVetor() {
        return vetor;
    }

}