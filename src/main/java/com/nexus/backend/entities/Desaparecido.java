package com.nexus.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Desaparecido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String apelido;
    private String rg;
    private String cpf;
    private LocalDate dataNascimento;
    private Boolean desapareceuAntes;
    private Integer idResponsavel;
    private String boletimOcorrencia;
    private LocalDate dataOcorrencia;
    private LocalDate dataComunicacao;
    private String imagem;
    private String cidade;
    private String sexo;
    private String estadoCivil;
    private String nomePai;
    private String nomeMae;
    private String cep;
    private String endereco;
    private String telefone;
    private String ultimoLocalVisto;
    private String corPele;
    private String corOlhos;
    private String roupaDesaparecimento;
}
