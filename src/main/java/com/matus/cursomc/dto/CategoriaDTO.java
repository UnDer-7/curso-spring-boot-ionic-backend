package com.matus.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.matus.cursomc.domain.Categoria;

// Serve para definir quais dados de Categoria podem ser trafegados
public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Campo obrigatorio")
	@Length(min=5, max=80, message="Tamanho deve ser entre 5 e 80 caracteres")
	private String nome;
	
	public CategoriaDTO() {	
	}
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
