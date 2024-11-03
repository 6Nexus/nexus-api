package com.nexus.backend.entities.curso.questionario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String pergunta;

    @ManyToOne
    Questionario questionario;

    @OneToOne
    Resposta respostaCerta;
}
