package br.com.gustavoantunes.model;

public class Papel extends Simbolo {

	@Override
	public int compareTo(Simbolo j) {
		if (this.getNome().equals(j.getNome())) {
			return 0;
		} else if (j.getNome().equals(TipoJogada.PEDRA) || j.getNome().equals(TipoJogada.SPOCK)) {
			return 1;
		}

		return -1;
	}

	@Override
	public TipoJogada getNome() {
		return TipoJogada.PAPEL;
	}

}
