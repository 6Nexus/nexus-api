package com.nexus.backend.entities;

import com.nexus.backend.enums.TipoUsuario;
//import com.nexus.backend.strategy.VisualizacaoCursoAssociado;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Associado extends Usuario {
//
//    public Associado() {
//        // Inicializa o tipo de usuário e a estratégia de visualização para Associado
//        this.setTipoUsuario(TipoUsuario.ASSOCIADO);
//        this.setVisualizacaoCursoStrategy(new VisualizacaoCursoAssociado());
//    }

    private String endereco;
    private String grauParentescoComDesaparecido;

//    Associado(Integer id, String nome, String email, String senha, String cpf, String telefone, TipoUsuario tipoUsuario) {
//        super(id, nome, email, senha, cpf, telefone, tipoUsuario);
//    }
}
