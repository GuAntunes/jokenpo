package br.com.gustavoantunes.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.gustavoantunes.dto.JogadaDTO;
import br.com.gustavoantunes.exception.JogadaException;
import br.com.gustavoantunes.model.Jogada;
import br.com.gustavoantunes.model.Jogador;
import br.com.gustavoantunes.model.Resultado;
import br.com.gustavoantunes.model.SimboloFactory;

public class JogadaServiceTest {

	private JogadaService jogadaService;

	@Before
	public void before() {
		jogadaService = new JogadaService();
	}

	@Test
	public void testFindJogadaByCodigoJogador() {

		// Testando com parâmetros null
		Integer codigo = null;
		List<Jogada> jogadas = null;

		assertNull(this.jogadaService.findJogadaByCodigoJogador(codigo, jogadas));

		// Testando com Jogada instanciada porém Jogada null
		jogadas = new ArrayList<>();
		jogadas.add(new Jogada(null, null));
		assertNull(this.jogadaService.findJogadaByCodigoJogador(codigo, jogadas));

		// Adiciona duas jogadas na lista de jogadas e realiza o filtro
		jogadas = new ArrayList<>();
		Jogador j1 = new Jogador(1, "Jogador 1");
		Jogador j2 = new Jogador(2, "Jogador 2");
		jogadas.add(new Jogada(j1, null));
		jogadas.add(new Jogada(j2, null));

		Jogada resp = this.jogadaService.findJogadaByCodigoJogador(1, jogadas);
		assertNotNull(resp);
		assertEquals(resp.getJogador(), j1);

	}

	@Test
	public void testDelete() {

		jogadaService.delete(null, null);

		List<Jogada> jogadas = null;

		jogadas = new ArrayList<>();
		Jogador j1 = new Jogador(1, "Jogador 1");
		Jogador j2 = new Jogador(2, "Jogador 2");
		jogadas.add(new Jogada(j1, null));
		jogadas.add(new Jogada(j2, null));

		jogadaService.delete(1, jogadas);

		assertEquals(jogadas.size(), 1);

	}

	// Teste de método private com Reflection
	@Test
	public void testDefineResultado() throws Exception {
		List<Jogada> jogadas = new ArrayList<>();

		Method method = JogadaService.class.getDeclaredMethod("defineResultado", List.class);
		method.setAccessible(true);
		Resultado returnValue = (Resultado) method.invoke(this.jogadaService, jogadas);

		assertNotNull(returnValue);
		assertNotNull(returnValue.getResultado());
		assertEquals("Nenhum dos jogadores ganhou a partida!", returnValue.getResultado());

		Jogador j1 = new Jogador(1, "Jogador 1");
		jogadas.add(new Jogada(j1, null));

		returnValue = (Resultado) method.invoke(this.jogadaService, jogadas);

		assertNotNull(returnValue);
		assertNotNull(returnValue.getResultado());
		assertEquals("O jogador Jogador 1 ganhou a partida!", returnValue.getResultado());

		Jogador j2 = new Jogador(2, "Jogador 2");
		jogadas.add(new Jogada(j2, null));

		returnValue = (Resultado) method.invoke(this.jogadaService, jogadas);

		String resp = "Houve um empate entre os jogadores: Jogador 1;Jogador 2;";
		assertNotNull(returnValue);
		assertNotNull(returnValue.getResultado());
		assertEquals(resp, returnValue.getResultado());
	}

	@Test
	public void testCalculoFatorialParaEncontrarGanhadores() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SimboloFactory factory = new SimboloFactory();
		List<Jogada> jogadas = null;

		Method method = JogadaService.class.getDeclaredMethod("calculoFatorialParaEncontrarGanhadores", List.class);
		method.setAccessible(true);
		List<Jogada> returnValue = (List<Jogada>) method.invoke(this.jogadaService, jogadas);
		assertNotNull(returnValue);
		assertTrue(returnValue.isEmpty());

		jogadas = new ArrayList<>();
		returnValue = (List<Jogada>) method.invoke(this.jogadaService, jogadas);
		assertNotNull(returnValue);
		assertTrue(returnValue.isEmpty());

		Jogador j1 = new Jogador(1, "Jogador 1");
		jogadas.add(new Jogada(j1, factory.createSimbolo("pedra")));
		returnValue = (List<Jogada>) method.invoke(this.jogadaService, jogadas);
		assertNotNull(returnValue);
		assertFalse(returnValue.isEmpty());

		Jogador j2 = new Jogador(2, "Jogador 2");
		jogadas.add(new Jogada(j2, factory.createSimbolo("papel")));
		returnValue = (List<Jogada>) method.invoke(this.jogadaService, jogadas);
		assertNotNull(returnValue);
		assertFalse(returnValue.isEmpty());
		assertTrue(returnValue.size() == 1);
		assertEquals(j2, returnValue.get(0).getJogador());

		Jogador j3 = new Jogador(2, "Jogador 3");
		jogadas.add(new Jogada(j2, factory.createSimbolo("tesoura")));
		returnValue = (List<Jogada>) method.invoke(this.jogadaService, jogadas);
		assertNotNull(returnValue);
		assertTrue(returnValue.isEmpty());

		jogadas = new ArrayList<>();
		Jogada jogada1 = new Jogada(j1, factory.createSimbolo("pedra"));
		Jogada jogada2 = new Jogada(j2, factory.createSimbolo("papel"));
		Jogada jogada3 = new Jogada(j3, factory.createSimbolo("papel"));
		jogadas.add(jogada1);
		jogadas.add(jogada2);
		jogadas.add(jogada3);
		returnValue = (List<Jogada>) method.invoke(this.jogadaService, jogadas);
		assertNotNull(returnValue);
		assertFalse(returnValue.isEmpty());
		assertTrue(returnValue.size() == 2);
		assertTrue(returnValue.contains(jogada2));
		assertTrue(returnValue.contains(jogada3));
		assertFalse(returnValue.contains(jogada1));

	}

	@Test(expected = JogadaException.class)
	public void testCalculaGanhadorNull() throws JogadaException {

		this.jogadaService.calculaGanhador(null);

	}

	@Test(expected = JogadaException.class)
	public void testCalculaGanhadorEmpty() throws JogadaException {
		List<Jogada> jogadas = new ArrayList<>();
		this.jogadaService.calculaGanhador(jogadas);

	}

	@Test(expected = JogadaException.class)
	public void testCalculaGanhadorComUmaJogada() throws JogadaException {
		List<Jogada> jogadas = new ArrayList<>();
		jogadas.add(new Jogada(null, null));
		this.jogadaService.calculaGanhador(jogadas);

	}

	@Test
	public void testCalculaGanhadorComDuasJogadas() throws JogadaException {
		SimboloFactory factory = new SimboloFactory();

		List<Jogada> jogadas = new ArrayList<>();
		jogadas.add(new Jogada(new Jogador(123, "Jogador 1"), factory.createSimbolo("pedra")));
		jogadas.add(new Jogada(new Jogador(123, "Jogador 1"), factory.createSimbolo("papel")));
		Resultado r = this.jogadaService.calculaGanhador(jogadas);
		assertNotNull(r);
		assertEquals("O jogador Jogador 1 ganhou a partida!", r.getResultado());

	}

	@Test
	public void testVerificaSeJogadorTemJogada() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SimboloFactory factory = new SimboloFactory();
		Method method = JogadaService.class.getDeclaredMethod("verificaSeJogadorTemJogada", Jogada.class, List.class);
		method.setAccessible(true);
		boolean returnValue = (boolean) method.invoke(this.jogadaService, null, null);
		assertFalse(returnValue);
		
		
		List<Jogada> jogadas = new ArrayList<>();
		Jogada jogada = new Jogada(null, null);
		jogadas.add(jogada);
		
		returnValue = (boolean) method.invoke(this.jogadaService, jogada, jogadas);
		assertFalse(returnValue);
		
		Jogador j1 = new Jogador(123, "Jogador 1");
		Jogada jogada2 = new Jogada(j1, factory.createSimbolo("papel"));
		returnValue = (boolean) method.invoke(this.jogadaService, jogada2, jogadas);
		assertFalse(returnValue);
		
		jogadas.add(jogada2);
		returnValue = (boolean) method.invoke(this.jogadaService, jogada2, jogadas);
		assertTrue(returnValue);
	}
	
	@Test(expected = JogadaException.class)
	public void testRegistraJogadaNull() throws JogadaException {
		this.jogadaService.registrarJogada(null, null);
	}
	
	
	@Test
	public void testRegistraJogadaTrue() throws JogadaException {
		SimboloFactory factory = new SimboloFactory();
		
		Jogador j1 = new Jogador(123, "Jogador 1");
		Jogador j2 = new Jogador(321, "Jogador 2");
		Jogada jogada1 = new Jogada(j1, factory.createSimbolo("papel"));
		
		List<Jogada> jogadas = new ArrayList<>();
		
		boolean resp = this.jogadaService.registrarJogada(jogada1, jogadas);
		assertTrue(resp);
	}
	
	@Test(expected = JogadaException.class)
	public void testRegistraJogadaFalse() throws JogadaException {
		SimboloFactory factory = new SimboloFactory();
		
		Jogador j1 = new Jogador(123, "Jogador 1");
		Jogador j2 = new Jogador(321, "Jogador 2");
		Jogada jogada1 = new Jogada(j1, factory.createSimbolo("papel"));
		
		List<Jogada> jogadas = new ArrayList<>();

		jogadas.add(jogada1);
		
		boolean resp = this.jogadaService.registrarJogada(jogada1, jogadas);
	}
	
	@Test(expected = JogadaException.class)
	public void testToJogadaNull() throws JogadaException {
		this.jogadaService.toJogada(null);
	}
	
	
	@Test(expected = JogadaException.class)
	public void testToJogadaEmpty() throws JogadaException {
		JogadaDTO j = new JogadaDTO();
		this.jogadaService.toJogada(j);
	}
	
}
