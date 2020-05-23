package br.com.gustavoantunes.dto;

import java.security.InvalidParameterException;

import br.com.gustavoantunes.exception.JogadaException;
import br.com.gustavoantunes.model.Jogada;
import br.com.gustavoantunes.model.Jogador;
import br.com.gustavoantunes.model.Simbolo;
import br.com.gustavoantunes.model.SimboloFactory;
import br.com.gustavoantunes.service.JogadorService;

public class JogadaDTO {

	private Integer idJogador;
	private String simbolo;

	public Integer getIdJogador() {
		return idJogador;
	}

	public String getSimbolo() {
		return simbolo;
	}

}
