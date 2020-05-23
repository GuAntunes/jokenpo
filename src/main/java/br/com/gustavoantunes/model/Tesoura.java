package br.com.gustavoantunes.model;

public class Tesoura extends Simbolo {

	@Override
	public int compareTo(Simbolo j) {
		if (this.getNome().equals(j.getNome())) {
			return 0;
		} else if (j.getNome().equals(TipoJogada.PAPEL) || j.getNome().equals(TipoJogada.LAGARTO)) {
			return 1;
		}

		return -1;
	}

	@Override
	public TipoJogada getNome() {
		return TipoJogada.TESOURA;
	}
}
