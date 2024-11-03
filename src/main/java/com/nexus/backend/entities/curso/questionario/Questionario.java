package com.nexus.backend.entities.curso.questionario;

import com.nexus.backend.entities.curso.Modulo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Questionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String descricao;

    @ManyToOne
    private Modulo modulo;
}
