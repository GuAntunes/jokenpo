package br.com.gustavoantunes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.com.gustavoantunes.dto.JogadaDTO;
import br.com.gustavoantunes.dto.JogadorDTO;
import br.com.gustavoantunes.model.Resultado;

@Controller
@RequestMapping("/")
public class JokenPoController {

	@Value("${jokenpo.url}")
	private String url;

	private void carregaInformacoes(Model model) {
		model.addAttribute("jogada", new JogadaDTO());
		model.addAttribute("jogadorExcluir", 0);
		model.addAttribute("jogadores", buscarJogadores());
		model.addAttribute("jogadas", buscarJogadas());
		model.addAttribute("jogador", new JogadorDTO());
	}

	private JogadorDTO[] buscarJogadores() {

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<JogadorDTO[]> response = restTemplate.getForEntity(url + "/jogador/list", JogadorDTO[].class);

		return response.getBody();
	}
	
	private JogadaDTO[] buscarJogadas() {

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<JogadaDTO[]> response = restTemplate.getForEntity(url + "/jogada/list", JogadaDTO[].class);

		return response.getBody();
	}

	private void excluirJogador(final Integer codigo) {
		// create an instance of RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.delete(url + "/jogador/" + codigo);

	}
	
	private void excluirJogada(final Integer codigo) {
		// create an instance of RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.delete(url + "/jogada/" + codigo);

	}
	
	private Resultado jogar() {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Resultado> response = restTemplate.postForEntity(url + "/jogada/jogar",null,  Resultado.class);

		return response.getBody();
	}

	@GetMapping
	public ModelAndView index(Model model) {
		carregaInformacoes(model);

		return new ModelAndView("index", model.asMap());
	}

	@PostMapping("/web/jogador")
	public ModelAndView salvarJogador(Model model, JogadorDTO jogadorDTO) {

		// create an instance of RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// request body parameters
		Map<String, String> map = new HashMap<>();
		map.put("codigo", jogadorDTO.getCodigo().toString());
		map.put("nome", jogadorDTO.getNome());

		// send POST request
		ResponseEntity<JogadorDTO> response = restTemplate.postForEntity(url + "/jogador", map, JogadorDTO.class);

		carregaInformacoes(model);

		return new ModelAndView("index", model.asMap());
	}

	@PostMapping("/web/jogador/deletar/{id}")
	public ModelAndView deletarJogador(Model model, @PathVariable Integer id) {

		this.excluirJogador(id);
		
		carregaInformacoes(model);
		
		return new ModelAndView("index", model.asMap());
	}
	
	@PostMapping("/web/jogada")
	public ModelAndView salvarJogada(Model model, JogadaDTO jogadaDTO) {

		RestTemplate restTemplate = new RestTemplate();

		// request body parameters
		Map<String, String> map = new HashMap<>();
		map.put("idJogador", jogadaDTO.getIdJogador().toString());
		map.put("simbolo", jogadaDTO.getSimbolo());

		// send POST request
		ResponseEntity<JogadaDTO> response = restTemplate.postForEntity(url + "/jogada", map, JogadaDTO.class);
		
		carregaInformacoes(model);

		return new ModelAndView("index", model.asMap());
	}

	@PostMapping("/web/jogada/deletar/{id}")
	public ModelAndView deletarJogada(Model model, @PathVariable Integer id) {

		this.excluirJogada(id);
		
		carregaInformacoes(model);
		
		return new ModelAndView("index", model.asMap());
	}
	
	@PostMapping("/web/jogar")
	public ModelAndView jogar(Model model) {

		Resultado r = this.jogar();
		
		model.addAttribute("resultado", r.getResultado());
		
		carregaInformacoes(model);
		
		return new ModelAndView("index", model.asMap());
	}
	
}
