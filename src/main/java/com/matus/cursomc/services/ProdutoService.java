package com.matus.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.matus.cursomc.domain.Categoria;
import com.matus.cursomc.domain.Produto;
import com.matus.cursomc.repositorys.CategoriaRepository;
import com.matus.cursomc.repositorys.ProdutoRepository;
import com.matus.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	//---------A classe SERVICE vai acessar o classe de REPOSITORY (private ProdutoRepository repo;)---------
	@Autowired// repo é automaticamente instanciando pelo Spring
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	//Criar uma operação q busca uma categoria por código
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: "+id+", Tipo: "+ Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		//categorias vai ser instanciando a partir da ids, então é necessário ir no BD buscar esses IDs
		// O repository busca todas as categorias correspondente a os IDs q tiver na lista ids
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		//search ==> Quando não tiver uma Query Method do Spring Data tem q criar a apropria JPQL (query da JPA q faz a consulta no BD)
		return repo.search(nome, categorias, pageRequest);
	}
}
