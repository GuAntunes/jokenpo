package br.com.gustavoantunes.model;

public class SimboloFactory {

	public Simbolo createSimbolo(String novoSimbolo) {

		switch (novoSimbolo) {
		case "pedra":
			return new Pedra();
		case "papel":
			return new Papel();
		case "tesoura":
			return new Tesoura();
		case "spock":
			return new Spock();
		case "lagarto":
			return new Lagarto();
		default:
			return null;
		}

	}

}
