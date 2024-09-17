package com.nexus.backend.entities;

import com.nexus.backend.enums.TipoUsuario;
import com.nexus.backend.strategy.VisualizacaoCursoAssociado;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Associado extends Usuario {

    public Associado() {
        // Inicializa o tipo de usuário e a estratégia de visualização para Associado
        this.setTipoUsuario(TipoUsuario.ASSOCIADO);
        this.setVisualizacaoCursoStrategy(new VisualizacaoCursoAssociado());
    }

    @NotBlank
    private String endereco;
    private String grauParentescoComDesaparecido;
}
