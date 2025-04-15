//package com.nexus.backend.dto.desaparecido;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Past;
//import jakarta.validation.constraints.PastOrPresent;
//import lombok.Builder;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Data
//@Builder
//public class DesaparecidoCriacaoDto {
//    @NotBlank
//    private String nome;
//    private String apelido;
//    private String rg;
//    @NotBlank
////    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")
//    private String cpf;
//    @NotNull
//    @Past
//    private LocalDate dataNascimento;
//    private Boolean desapareceuAntes;
//    private Integer idResponsavel;
//    @NotBlank
//    private String boletimOcorrencia;
//    @PastOrPresent
//    private LocalDate dataOcorrencia;
//    private LocalDate dataComunicacao;
//    @NotBlank
//    private String imagem;
//    private String cidade;
//    private String sexo;
//    private String estadoCivil;
//    private String nomePai;
//    private String nomeMae;
//    private String cep;
//    private String endereco;
//    private String telefone;
//    @NotBlank
//    private String ultimoLocalVisto;
//    private String corPele;
//    private String corOlhos;
//    private String roupaDesaparecimento;
//}
