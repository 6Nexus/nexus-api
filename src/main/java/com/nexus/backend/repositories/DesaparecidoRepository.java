//package com.nexus.backend.repositories;
//
//import com.nexus.backend.entities.Desaparecido;
//import com.nexus.backend.entities.Usuario;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//public interface DesaparecidoRepository extends JpaRepository<Desaparecido, Integer> {
//
//    List<Desaparecido> findByNomeContainsIgnoreCase(String nome);
//
//    List<Desaparecido> findByDataOcorrenciaBetween(LocalDate inicio, LocalDate fim);
//
//}
