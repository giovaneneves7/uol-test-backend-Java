package com.github.giovaneneves7.api.entity.jogador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogadorPostResponseDto {

    private Long id;
    private String codinome;

}
