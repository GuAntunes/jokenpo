package br.com.gustavoantunes.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavoantunes.dto.JogadaDTO;
import br.com.gustavoantunes.exception.JogadaException;
import br.com.gustavoantunes.model.Jogada;
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

			return ResponseEntity.ok(jogada);

		} catch (JogadaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

}
