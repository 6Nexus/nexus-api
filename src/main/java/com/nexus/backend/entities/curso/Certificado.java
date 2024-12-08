package com.nexus.backend.entities.curso;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificado {
    private LocalDateTime dataConclusao;
    @OneToOne
    private Curso curso;
}
