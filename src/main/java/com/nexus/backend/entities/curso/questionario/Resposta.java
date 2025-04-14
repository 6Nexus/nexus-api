package com.nexus.backend.entities.curso.questionario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String resposta;

    @ManyToOne
    @JoinColumn(name = "pergunta_id")
    Pergunta pergunta;
}
