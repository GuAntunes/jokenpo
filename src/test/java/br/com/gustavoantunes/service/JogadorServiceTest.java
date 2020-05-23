package br.com.gustavoantunes.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import br.com.gustavoantunes.model.Jogador;

public class JogadorServiceTest {

	@Before
	public void before() {
	}
	
	
	@Test
	public void testSave() {
		
		assertNull(JogadorService.save(null));
		
		Jogador jogador = new Jogador(123, "Jogador 1");
		
		assertEquals(JogadorService.save(jogador), jogador);
	}

}
