package com.github.giovaneneves7.api.entity.jogador.service;

import com.github.giovaneneves7.api.entity.jogador.dto.JogadorGetResponseDto;
import com.github.giovaneneves7.api.entity.jogador.dto.JogadorPostResponseDto;
import com.github.giovaneneves7.api.entity.jogador.model.Jogador;
import java.util.List;

/**
 * Interface do serviço da entidade 'Jogador'.
 *
 * @author Giovane Neves
 */
public interface IJogadorService {

    JogadorPostResponseDto saveJogador(Jogador jogador);
    List<JogadorGetResponseDto> getAllJogadores();

}
