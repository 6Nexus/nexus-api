package com.nexus.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexus.backend.dto.CursoDto;
import com.nexus.backend.enums.TipoUsuario;
import com.nexus.backend.strategy.VisualizacaoCursoStrategy;
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

    //    @NotBlank
    private String nome;
    //    @Email
    private String email;
    private String senha;
    //    @NotBlank
//    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")
    private String cpf;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

//    @Transient  // Indica ao Hibernate que este campo não será persistido no banco de dados
//    @JsonIgnore // Ignora esse campo na serialização/desserialização
//    protected VisualizacaoCursoStrategy visualizacaoCursoStrategy;
//
//    // Método para usar a estratégia de visualização de curso
//    public CursoDto visualizarCurso(Integer cursoId) {
//        if (visualizacaoCursoStrategy != null) {
//            return visualizacaoCursoStrategy.visualizarCurso(cursoId);
//        } else {
//            throw new UnsupportedOperationException("Estratégia de visualização não definida!");
//        }
//    }
}
