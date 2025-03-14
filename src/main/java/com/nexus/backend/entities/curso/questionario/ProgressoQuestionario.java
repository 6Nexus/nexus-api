package com.nexus.backend.entities.curso.questionario;


import com.nexus.backend.entities.curso.Matricula;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressoQuestionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double pontuacao;
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "matricula_id")
    private Matricula matricula;

    @ManyToOne
    @JoinColumn(name = "questionario_id")
    private Questionario questionario;

}
