package com.nexus.backend.entities.curso;

import com.nexus.backend.entities.Associado;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean certificadoEmitido;

    @ManyToOne
    private Associado associado;

    @ManyToOne
    private Curso curso;
}
