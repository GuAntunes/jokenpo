package br.com.gustavoantunes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.gustavoantunes.dto.JogadaDTO;
import br.com.gustavoantunes.exception.JogadaException;
import br.com.gustavoantunes.model.Jogada;
import br.com.gustavoantunes.model.Jogador;
import br.com.gustavoantunes.model.Simbolo;
import br.com.gustavoantunes.model.SimboloFactory;

@Service
public class JogadaService {

	public Jogada toJogada(JogadaDTO jogadaDTO) throws JogadaException {

		SimboloFactory simboloFactory = new SimboloFactory();

		if (jogadaDTO != null && jogadaDTO.getIdJogador() > 0 && jogadaDTO.getSimbolo() != null) {
			Jogador j = JogadorService.findOne(jogadaDTO.getIdJogador());
			if (j != null) {
				Simbolo simbolo = simboloFactory.createSimbolo(jogadaDTO.getSimbolo());
				if (simbolo != null) {
					return new Jogada(j, simbolo);
				}
				throw new JogadaException("Simbolo não encontrado");
			}
			throw new JogadaException("Jogador não encontrado");
		}

		throw new JogadaException("Campos Inválidos");
	}

	public boolean registrarJogada(Jogada jogada, List<Jogada> jogadas) throws JogadaException {
		if(!verificaSeJogadorTemJogada(jogada, jogadas)) {
			jogadas.add(jogada);
			return true;
		}
		throw new JogadaException("O jogador já possui uma jogada!");
	}

	private boolean verificaSeJogadorTemJogada(Jogada jogada, List<Jogada> jogadas) {

		for (Jogada j : jogadas) {
			if (j.getJogador().getCodigo().equals(jogada.getJogador().getCodigo())) {
				return true;
			}
		}
		return false;
	}

}
