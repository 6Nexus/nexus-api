package com.nexus.backend.dto.desaparecido;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DesaparecidoCriacaoDto {
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
