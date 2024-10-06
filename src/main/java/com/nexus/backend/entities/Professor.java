package com.nexus.backend.entities;

import com.nexus.backend.enums.TipoUsuario;
import com.nexus.backend.strategy.VisualizacaoCursoProfessor;
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

//    public Professor() {
//        // Inicializa o tipo de usuário e a estratégia de visualização para Professor
//        this.setTipoUsuario(TipoUsuario.PROFESSOR);
////        this.setVisualizacaoCursoStrategy(new VisualizacaoCursoProfessor());
//    }

    private String areaAtuacao;

    private Boolean aprovado;

}
