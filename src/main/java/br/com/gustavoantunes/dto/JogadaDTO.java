package br.com.gustavoantunes.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.gustavoantunes.model.Jogada;

public class JogadaDTO {

	private Integer idJogador;
	private String simbolo;


	public JogadaDTO() {
	}
	
	public JogadaDTO(Jogada jogada) {
		this.idJogador = jogada.getJogador().getCodigo();
		this.simbolo = jogada.getSimbolo().getNome().getTipo();
	}

	public Integer getIdJogador() {
		return idJogador;
	}

	public String getSimbolo() {
		return simbolo;
	}

	
	public static List<JogadaDTO> converter(List<Jogada> jogadas)	{
		return jogadas.stream().map(JogadaDTO::new)
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
