package com.nexus.backend.entities.curso;

import com.nexus.backend.entities.Professor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String descricao;
    private Integer ordem;
    private LocalDateTime criadoEm;

    @ManyToOne
    private Curso curso;
}
