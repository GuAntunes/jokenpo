package br.com.gustavoantunes.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.gustavoantunes.model.Jogada;
import br.com.gustavoantunes.model.Jogador;
import br.com.gustavoantunes.model.Resultado;

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
		
		String resp = "Houve um empate entre os jogadores: \nJogador 1\nJogador 2";
		assertNotNull(returnValue);
		assertNotNull(returnValue.getResultado());
		assertEquals(resp, returnValue.getResultado());
	}
	
}
