package com.nexus.backend.entities;

import com.nexus.backend.enums.TipoUsuario;
//import com.nexus.backend.strategy.VisualizacaoCursoProfessor;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Builder
public class Professor extends Usuario {

    private String areaAtuacao;

    private Boolean aprovado;

}
