package br.com.gustavoantunes.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavoantunes.dto.JogadorDTO;
import br.com.gustavoantunes.exception.JogadaException;
import br.com.gustavoantunes.model.Jogador;
import br.com.gustavoantunes.service.JogadorService;

@RestController
@RequestMapping("/jogador")
public class JogadorResource {

	@GetMapping("{id}")
	public ResponseEntity<?> findOne(@PathVariable Integer id) {
		// O valor passado como parâmetro é válido
		if (id != null && id > 0) {
			// Busca um usuário através do código
			Jogador jogador = JogadorService.findOne(id);
			// Verifica se o jogador existe
			if (jogador != null) {
				// retorna o jogador
				return ResponseEntity.ok(new JogadorDTO(jogador));
			}
			// Se o jogador não existe retorna código 404 not found
			return ResponseEntity.notFound().build();
		}
		// Se o valor é inválido retorna código 400 Bad Request
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/list")
	public ResponseEntity<List<JogadorDTO>> list() {
		// Busca todos os jogadores cadastrados
		return ResponseEntity.ok(JogadorDTO.converter(JogadorService.findAll()));
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody JogadorDTO jogadorDTO) {
		if (jogadorDTO != null && jogadorDTO.getCodigo() != null && jogadorDTO.getCodigo() > 0
				&& jogadorDTO.getNome() != null && !jogadorDTO.getNome().trim().isEmpty()) {

			Jogador jogador = jogadorDTO.toJogador();
			try {
				jogador = JogadorService.save(jogador);
			} catch (JogadaException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			return ResponseEntity.ok(new JogadorDTO(jogador));
		}

		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		// O valor passado como parâmetro é válido
		if (id != null && id > 0) {
			// Busca um usuário através do código
			if (JogadorService.findOne(id) != null) {
				JogadorService.delete(id);
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogador não encontrado!");
		}

		return ResponseEntity.badRequest().body("Campos Inválidos!");
	}

}
