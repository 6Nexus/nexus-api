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
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private Integer ordem;

    @ManyToOne
    private Curso curso;
}
