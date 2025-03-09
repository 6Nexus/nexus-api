package com.nexus.backend.entities.curso.video;

import com.nexus.backend.entities.Professor;
import com.nexus.backend.entities.curso.Modulo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String descricao;
    private Integer ordem;
    private Boolean carregadoNoYoutube;
    private String path;
    private String youtubeUrl;
    private LocalDateTime criadoEm;

    @ManyToOne
    private Modulo modulo;
}