package com.matus.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matus.cursomc.Repositorys.CategoriaRepository;
import com.matus.cursomc.domain.Categoria;

@Service
public class CategoriaService {
	
	//---------A classe SERVICE vai acessar o classe de REPOSITORY (private CategoriaRepository repo;)---------
	@Autowired// repo é automaticamente instaciando pelo Spring
	private CategoriaRepository repo;
	
	//Criar uma operacao q busca uma categoria por codigo
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
