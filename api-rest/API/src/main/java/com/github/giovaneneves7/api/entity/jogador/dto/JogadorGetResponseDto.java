package com.github.giovaneneves7.api.entity.jogador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogadorGetResponseDto {

    private Long id;
    private String nome;
    private String email;
    private long telefone;
    private String codinome;
    private String grupo;

}
