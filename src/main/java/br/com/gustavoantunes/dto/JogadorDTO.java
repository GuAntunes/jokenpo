package br.com.gustavoantunes.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.gustavoantunes.model.Jogador;

public class JogadorDTO implements Serializable{

	private Integer codigo;

	private String nome;

	public JogadorDTO(Integer codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public JogadorDTO() {
		// TODO Auto-generated constructor stub
	}

	public JogadorDTO(Jogador user) {
		this.codigo = user.getCodigo();
		this.nome = user.getNome();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public Jogador toJogador() {
		return new Jogador(this.codigo, this.nome);
	}

	public static List<JogadorDTO> converter(List<Jogador> usuarios) {
		List<JogadorDTO> usuariosDTO = usuarios.stream().map(JogadorDTO::new)
				.collect(Collectors.toCollection(ArrayList::new));
		return usuariosDTO;

	}
}