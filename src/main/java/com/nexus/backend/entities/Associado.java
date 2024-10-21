package com.nexus.backend.entities;

import com.nexus.backend.enums.TipoUsuario;
//import com.nexus.backend.strategy.VisualizacaoCursoAssociado;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Associado extends Usuario {

    private String endereco;
    private String grauParentescoComDesaparecido;
}
