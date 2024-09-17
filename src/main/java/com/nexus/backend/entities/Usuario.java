package com.nexus.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexus.backend.dto.CursoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.nexus.backend.enums.TipoUsuario;
import com.nexus.backend.strategy.VisualizacaoCursoStrategy;

@Getter
@Setter
@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Transient  // Indica ao Hibernate que este campo não será persistido no banco de dados
    @JsonIgnore // Ignora esse campo na serialização/desserialização
    protected VisualizacaoCursoStrategy visualizacaoCursoStrategy;

    // Método para usar a estratégia de visualização de curso
    public CursoDto visualizarCurso(Integer cursoId) {
        if (visualizacaoCursoStrategy != null) {
            return visualizacaoCursoStrategy.visualizarCurso(cursoId);
        } else {
            throw new UnsupportedOperationException("Estratégia de visualização não definida!");
        }
    }
}
