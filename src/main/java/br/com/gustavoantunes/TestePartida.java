package br.com.gustavoantunes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.gustavoantunes.model.Jogada;
import br.com.gustavoantunes.model.Jogador;
import br.com.gustavoantunes.model.SimboloFactory;
import br.com.gustavoantunes.model.StatusVencedor;

public class TestePartida {

	public static void main(String[] args) {
	
		// Realiza cadastro dos jogadores
		Jogador j1 = new Jogador(1, "Jogador 01");
		Jogador j2 = new Jogador(2, "Jogador 02");
		Jogador j3 = new Jogador(3, "Jogador 03");

		// Cria lista com todos os jogadores cadastrados
		List<Jogador> jogadores = Arrays.asList(j1, j2, j3);

		// Cria uma fábrica de Simbolo
		SimboloFactory factory = new SimboloFactory();

		// Realiza criação de uma jogada
		Jogada jogada1 = new Jogada(j1, factory.createSimbolo("spock"));
		Jogada jogada2 = new Jogada(j2, factory.createSimbolo("tesoura"));
		Jogada jogada3 = new Jogada(j3, factory.createSimbolo("papel"));

		// Cria uma lista de jogadas
		List<Jogada> jogadas = Arrays.asList(jogada1, jogada2, jogada3);

		List<Jogada> ganhadores = new ArrayList<>();

		for (int x = 0; x < jogadas.size(); x++) {
			for (int y = x + 1; y < jogadas.size(); y++) {
				jogadas.get(x).comparar(jogadas.get(y));
			}
			if(jogadas.get(x).getStatus().equals(StatusVencedor.GANHOU)) {
				ganhadores.add(jogadas.get(x));
			}
		}

		if (ganhadores.size() == 0) {
			System.out.println("Ninguem ganhou a partida");
		} else if (ganhadores.size() == 1) {
			System.out.println("O jogador " + ganhadores.get(0).getJogador().getNome() + " ganhou a partida!");
		} else if (ganhadores.size() > 1) {
			System.out.println("Houve um empate entre os jogadores: ");
			for (Jogada jogada : ganhadores) {
				System.out.println(jogada.getJogador().getNome());
			}
		}

	}

}
