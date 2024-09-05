package com.nexus.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private String rg;
    private String cpf;
    private LocalDate dataNascimento;
    private String enderecoNascimento;
    private String grauParentescoDesaparecido;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEnderecoNascimento() {
        return enderecoNascimento;
    }

    public void setEnderecoNascimento(String enderecoNascimento) {
        this.enderecoNascimento = enderecoNascimento;
    }

    public String getGrauParentescoDesaparecido() {
        return grauParentescoDesaparecido;
    }

    public void setGrauParentescoDesaparecido(String grauParentescoDesaparecido) {
        this.grauParentescoDesaparecido = grauParentescoDesaparecido;
    }
}
