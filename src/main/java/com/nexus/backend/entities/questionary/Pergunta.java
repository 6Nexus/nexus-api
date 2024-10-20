package com.nexus.backend.entities.questionary;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
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
