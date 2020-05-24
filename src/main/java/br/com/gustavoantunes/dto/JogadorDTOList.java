package br.com.gustavoantunes.dto;

import java.util.ArrayList;
import java.util.List;

public class JogadorDTOList {

	private List<JogadorDTO> jogadores;

	public JogadorDTOList() {
		jogadores = new ArrayList<>();
	}

	public List<JogadorDTO> getJogadores() {
		return jogadores;
	}

	public void setJogadores(List<JogadorDTO> jogadores) {
		this.jogadores = jogadores;
	}

}
