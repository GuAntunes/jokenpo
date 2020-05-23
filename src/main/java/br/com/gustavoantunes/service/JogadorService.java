package br.com.gustavoantunes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.gustavoantunes.model.Jogador;

@Service
public class JogadorService {

	private static List<Jogador> jogadores = new ArrayList<>();

	public static Jogador findOne(int codigo) {

		return jogadores.stream().filter(u -> u.getCodigo().equals(codigo)).findAny().orElse(null);

	}

	public static List<Jogador> findAll() {
		return JogadorService.jogadores;
	}

	public static void delete(Integer codigo) {
		Jogador j = JogadorService.findOne(codigo);
		if (j != null) {
			jogadores.remove(j);
		}

	}

	public static Jogador save(Jogador jogador) {
		if (jogador != null)
			jogadores.add(jogador);
		return jogador;
	}

	
}
