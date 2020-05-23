package br.com.gustavoantunes.model;

public class Pedra extends Simbolo {

	@Override
	public TipoJogada getNome() {
		return TipoJogada.PEDRA;
	}

	@Override
	public int compareTo(Simbolo j) {
		if (this.getNome().equals(j.getNome())) {
			return 0;
		} else if (j.getNome().equals(TipoJogada.LAGARTO) || j.getNome().equals(TipoJogada.TESOURA)) {
			return 1;
		}

		return -1;
	}

}
