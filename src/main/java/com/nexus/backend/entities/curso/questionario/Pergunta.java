package com.nexus.backend.entities.curso.questionario;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @JoinColumn(name = "questionario_id")
    Questionario questionario;

    @OneToOne
    @JoinColumn(name = "resposta_certa_id", referencedColumnName = "id")
    private Resposta respostaCerta;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas;
}
