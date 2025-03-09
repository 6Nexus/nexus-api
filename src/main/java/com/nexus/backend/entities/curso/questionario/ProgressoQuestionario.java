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
    private Matricula matricula;

    @ManyToOne
    private Questionario questionario;

}
