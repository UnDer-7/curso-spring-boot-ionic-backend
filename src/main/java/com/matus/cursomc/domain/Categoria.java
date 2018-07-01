package com.matus.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

// Serializable ==> Faz com q os obj da classa possam ser convertidos em bytes,
// com isso eles podem ser transefiros para arquivos, navegar na rede etc.
//Entity ==> Cria uma tabela no BD com os campos da classe
@Entity
public class Categoria implements Serializable {
	// Quando usar o Serializable tem q utilzar o serialVersionUID
	private static final long serialVersionUID = 1L;

	// @Id e @GeneratedValue ==> Gera uma chave primaria no BD
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;

	// Uma Categoria pode ter varios produtos, por isso cria uma array
	@JsonManagedReference // Faz isso no lada q vc quer q venha os objs associados
	@ManyToMany(mappedBy="categorias")
	private List<Produto> produtos = new ArrayList<>();

	public Categoria() {
	}

	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}