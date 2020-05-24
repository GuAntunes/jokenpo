package br.com.gustavoantunes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.gustavoantunes.dto.JogadaDTO;
import br.com.gustavoantunes.exception.JogadaException;
import br.com.gustavoantunes.model.Jogada;
import br.com.gustavoantunes.model.Jogador;
import br.com.gustavoantunes.model.Resultado;
import br.com.gustavoantunes.model.Simbolo;
import br.com.gustavoantunes.model.SimboloFactory;
import br.com.gustavoantunes.model.StatusVencedor;

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
		if (!verificaSeJogadorTemJogada(jogada, jogadas)) {
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

	public Resultado calculaGanhador(List<Jogada> jogadas) throws JogadaException {

		if (jogadas != null && jogadas.size() > 1) {

			List<Jogada> ganhadores = calculoFatorialParaEncontrarGanhadores(jogadas);

			return defineResultado(ganhadores);
		}

		throw new JogadaException("É necessário ter pelo menos duas jogadas criadas para jogar jokenpo!");

	}

	// Realiza calculo fatorial, para checar todas as combinações possíveis, com o
	// mínimo de tentativas e filtrar os ganhadores
	private List<Jogada> calculoFatorialParaEncontrarGanhadores(List<Jogada> jogadas) {
		List<Jogada> ganhadores = new ArrayList<>();

		for (int x = 0; x < jogadas.size(); x++) {
			for (int y = x + 1; y < jogadas.size(); y++) {
				jogadas.get(x).comparar(jogadas.get(y));
			}
			if (jogadas.get(x).getStatus().equals(StatusVencedor.GANHOU)) {
				ganhadores.add(jogadas.get(x));
			}
		}

		return ganhadores;
	}

	private Resultado defineResultado(List<Jogada> ganhadores) {

		
		Resultado r = new Resultado();

		if (ganhadores.size() == 0) {
			r.setResultado("Nenhum dos jogadores ganhou a partida!");
		} else if (ganhadores.size() == 1) {
			r.setResultado("O jogador " + ganhadores.get(0).getJogador().getNome() + " ganhou a partida!");
		} else if (ganhadores.size() > 1) {
			String resultado = "Houve um empate entre os jogadores: ";
			for (Jogada jogada : ganhadores) {
				resultado +=  jogada.getJogador().getNome() + ";";
			}
			r.setResultado(resultado.toString());
		}

		return r;
	}

	public Jogada findJogadaByCodigoJogador(Integer codigo, List<Jogada> jogadas) {
			return jogadas != null ? jogadas.stream()
			.filter(j -> j.getJogador() != null && j.getJogador().getCodigo() != null && j.getJogador().getCodigo().equals(codigo)).findAny().orElse(null) : null;
		 

	}

	public void delete(Integer id, List<Jogada> jogadas) {
		Jogada j = this.findJogadaByCodigoJogador(id, jogadas);
		if (j != null) {
			jogadas.remove(j);
		}
	}

}
