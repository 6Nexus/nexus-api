package com.nexus.backend.entities.curso;

import com.nexus.backend.entities.Associado;
import com.nexus.backend.entities.curso.questionario.ProgressoQuestionario;
import com.nexus.backend.entities.curso.video.ProgressoVideo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean certificadoEmitido;

    @ManyToOne
    private Associado associado;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgressoQuestionario> progressosQuestionario;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgressoVideo> progressosVideo;
}
