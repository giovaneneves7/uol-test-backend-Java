package com.github.giovaneneves7.api.entity.jogador.repository;

import com.github.giovaneneves7.api.entity.jogador.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IJogadorRepository extends JpaRepository<Jogador, Long> {

    @Query("SELECT j.codinome FROM Jogador j WHERE j.grupo = :grupo")
    List<String> findAllCodinome(@Param("grupo") String grupo);
}
