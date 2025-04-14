package com.nexus.backend.entities.curso.questionario;

import com.nexus.backend.entities.curso.Modulo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;

    @OneToMany(mappedBy = "questionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgressoQuestionario> progressosQuestionario;

    @OneToMany(mappedBy = "questionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pergunta> perguntas;
}
