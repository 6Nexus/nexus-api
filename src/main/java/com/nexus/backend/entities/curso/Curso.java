package com.nexus.backend.entities.curso;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexus.backend.entities.Professor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String categoria;
    private String descricao;

    @JsonIgnore
    @Column(length = 10 * 1024 * 1024) // 10 Mega Bytes
    private byte[] capa;

    @ManyToOne
    private Professor professor;


}
