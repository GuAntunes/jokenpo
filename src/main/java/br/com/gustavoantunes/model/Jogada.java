package br.com.gustavoantunes.model;

public class Jogada {

	private Jogador jogador;
	private Simbolo simbolo;
	private StatusVencedor status = StatusVencedor.GANHOU;

	public Jogada(Jogador jogador, Simbolo simbolo) {
		this.jogador = jogador;
		this.simbolo = simbolo;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public Simbolo getSimbolo() {
		return simbolo;
	}

	public StatusVencedor getStatus() {
		return status;
	}

	public void setStatus(StatusVencedor status) {
		this.status = status;
	}

	public void comparar(Jogada jogada) {

		int retorno = this.simbolo.compareTo(jogada.getSimbolo());

		if (retorno == -1 && status.equals(StatusVencedor.GANHOU)) {
			status = StatusVencedor.PERDEU;
		} else if (retorno == 1 && jogada.getStatus().equals(StatusVencedor.GANHOU)) {
			jogada.setStatus(StatusVencedor.PERDEU);
		}

	}

}
