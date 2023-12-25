package com.github.giovaneneves7.api.entity.jogador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogadorPostRequestDto {

    @NotNull(message = "O nome do jogador é obrigatório")
    @NotBlank(message = "O nome do jogador não pode ser vazio")
    private String nome;

    @NotNull(message = "O email do jogador é obrigatório")
    @NotBlank(message = "O email do jogador não pode ser vazio")
    private String email;
    private long telefone;

    @NotNull(message = "O grupo do jogador é obrigatório")
    private String grupo;

}
