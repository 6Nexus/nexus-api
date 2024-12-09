package com.nexus.backend.entities.curso.video;

import com.nexus.backend.entities.curso.Matricula;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressoVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean assistido;

    @ManyToOne
    private Matricula matricula;

    @ManyToOne
    private Video video;

}
