package com.nexus.backend.entities.curso;

import com.nexus.backend.entities.Associado;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curtida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Associado associado;

    @ManyToOne
    private Curso curso;
}
