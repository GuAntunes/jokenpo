package br.com.gustavoantunes.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavoantunes.dto.JogadaDTO;
import br.com.gustavoantunes.exception.JogadaException;
import br.com.gustavoantunes.model.Jogada;
import br.com.gustavoantunes.model.Resultado;
import br.com.gustavoantunes.service.JogadaService;

@RestController
@RequestMapping("/jogada")
public class JogadaResource {

	@Autowired
	private JogadaService jogadaService;

	private List<Jogada> jogadas = new ArrayList<>();

	@PostMapping
	public ResponseEntity<?> criarJogada(@RequestBody JogadaDTO jogadaDTO) {

		try {
			Jogada jogada = jogadaService.toJogada(jogadaDTO);
			
			jogadaService.registrarJogada(jogada, jogadas);

			return ResponseEntity.ok(new JogadaDTO(jogada));

		} catch (JogadaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping("{id}")
	public ResponseEntity<?> findJogadaByCodigoJogador(@PathVariable Integer id) {
		// O valor passado como parâmetro é válido
		if (id != null && id > 0) {
			// Busca um usuário através do código
			Jogada jogada = jogadaService.findJogadaByCodigoJogador(id, jogadas);
			// Verifica se o jogador existe
			if (jogada != null) {
				// retorna o jogador
				return ResponseEntity.ok(new JogadaDTO(jogada));
			}
			// Se o jogador não existe retorna código 404 not found
			return ResponseEntity.notFound().build();
		}
		// Se o valor é inválido retorna código 400 Bad Request
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/list")
	public ResponseEntity<List<JogadaDTO>> list() {
		// Retorna todas as Jogadas cadastradas
		return ResponseEntity.ok(JogadaDTO.converter(this.jogadas));
	}

	@PostMapping("/jogar")
	public ResponseEntity<?> jogar() {

		try {
			Resultado resultado = jogadaService.calculaGanhador(jogadas);

			// Após completar a execução das jogadas, limpa os elementos para uma nova
			// rodada
			jogadas.clear();

			return ResponseEntity.ok(resultado);
		} catch (JogadaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		// O valor passado como parâmetro é válido
		if (id != null && id > 0) {
			// Busca um usuário através do código
			if (jogadaService.findJogadaByCodigoJogador(id, jogadas) != null) {
				jogadaService.delete(id, jogadas);
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogador não encontrado!");
		}

		return ResponseEntity.badRequest().build();
	}

}
