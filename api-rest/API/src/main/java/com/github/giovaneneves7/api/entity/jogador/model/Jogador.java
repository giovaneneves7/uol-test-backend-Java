package com.github.giovaneneves7.api.entity.jogador.model;

import com.github.giovaneneves7.api.infrastructure.model.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um jogador.
 *
 * @author Giovane Neves
 */
@Entity
@Table(name = "jogadores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private long telefone;
    private String codinome;
    private String grupo;
}
