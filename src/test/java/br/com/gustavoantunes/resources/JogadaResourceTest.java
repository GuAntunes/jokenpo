package br.com.gustavoantunes.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.gustavoantunes.dto.JogadaDTO;
import br.com.gustavoantunes.exception.JogadaException;
import br.com.gustavoantunes.service.JogadaService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class JogadaResourceTest {

	private MockMvc mvc;

	@InjectMocks
	private JogadaResource jogadaResource = new JogadaResource();

	@Mock
	private JogadaService jogadaService;

	private final String rota = "/jogada";

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		this.mvc = MockMvcBuilders.standaloneSetup(jogadaResource).build();
	}

	@Test
	public void criarJogadaValoresNull() throws Exception {

		JogadaDTO jogadaDTO = new JogadaDTO();

		when(jogadaService.toJogada(any(JogadaDTO.class))).thenThrow(JogadaException.class);

		MvcResult result = mvc.perform(post(rota).flashAttr("jogadaDTO", jogadaDTO)).andExpect(status().is(400))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println(content);

	}

	@Test
	public void criarJogadaValoresJogadorNull() throws Exception {
		JogadaDTO jogadaDTO = new JogadaDTO();
		jogadaDTO.setSimbolo("pedra");
		MvcResult result = mvc.perform(post(rota).flashAttr("jogadaDTO", jogadaDTO)).andExpect(status().is(400))
				.andReturn();
	}

	@Test
	public void criarJogadaValoresSimboloNull() throws Exception {
		JogadaDTO jogadaDTO = new JogadaDTO();
		jogadaDTO.setIdJogador(123);
		MvcResult result = mvc.perform(post(rota).flashAttr("jogadaDTO", jogadaDTO)).andExpect(status().is(400))
				.andReturn();
	}
	
	@Test
	public void getJogadas() throws Exception {

		MvcResult result = mvc.perform(get(rota + "/list")).andExpect(status().is(200)).andReturn();
		String content = result.getResponse().getContentAsString();
		assertEquals("[]", content);
	}

}
