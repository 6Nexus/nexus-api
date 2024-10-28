package com.nexus.backend.entities.curso;

import com.nexus.backend.entities.Professor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String categoria;
    private String descricao;

    @ManyToOne
    private Professor professor;
}
