package com.github.giovaneneves7.api.controller.v1;

import com.github.giovaneneves7.api.entity.jogador.dto.JogadorPostRequestDto;
import com.github.giovaneneves7.api.entity.jogador.service.IJogadorService;
import com.github.giovaneneves7.api.entity.jogador.model.Jogador;

import com.github.giovaneneves7.api.infrastructure.util.ObjectMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controler para receber requisições HTTP direcionadas
 * à entidade 'Jogador'.
 *
 * @author Giovane Neves.
 */
@RestController
@RequestMapping(path = "/api/v1/json/")
public class JogadorController {

    @Autowired
    private ObjectMapperUtil _objectMapperUtil;

    @Autowired
    private IJogadorService _jogadorService;

    @PostMapping(path = "/jogadores/jogador", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> salvarJogador(@RequestBody JogadorPostRequestDto jogadorDto,
                                           BindingResult result){

        return ResponseEntity.status(HttpStatus.OK)
                .body(this._jogadorService.salvarJogador(this._objectMapperUtil.map(jogadorDto, new Jogador())));
    }

    @GetMapping(path = "/jogadores", produces = "application/json")
    public ResponseEntity<?> listarJogadores(){

        return ResponseEntity.status(HttpStatus.OK)
                .body(this._jogadorService.listarJogadores());

    }

    @GetMapping(path = "/jogadores/jogador/{id}", produces = "application/json")
    public ResponseEntity<?> pegarJogadorPeloId(@PathVariable("id") Long id){

        // TODO: Adicionar lógica.
        return null;
    }

    @PutMapping(path = "/jogadores/jogador", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> atualizarJogador(/*TODO: Criar DTO para requesição PUT*/){

        // TODO: Adicionar lógica.
        return null;
    }
    @DeleteMapping(path = "/jogadores/jogador/{id}", produces = "application/json")
    public ResponseEntity<?> deletarJogadorPorId(@PathVariable("id") Long id){

        // TOOD: Adicionar lógica.

        return null;
    }
}
