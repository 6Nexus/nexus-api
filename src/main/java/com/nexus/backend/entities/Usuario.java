package com.nexus.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexus.backend.dto.CursoDto;
import com.nexus.backend.enums.TipoUsuario;
//import com.nexus.backend.strategy.VisualizacaoCursoStrategy;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
//@Builder
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;

    private String email;
    private String senha;

//    private String cpf;
//    private String telefone;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;


}
