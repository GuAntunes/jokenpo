package br.com.gustavoantunes.model;

public enum TipoJogada {

	PEDRA("pedra"), PAPEL("papel"), TESOURA("tesoura"), LAGARTO("lagarto"), SPOCK("spock");
	
	private final String tipo;
	
	private TipoJogada(String tipo) {
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
}
