package com.matus.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.matus.cursomc.domain.Categoria;
import com.matus.cursomc.dto.CategoriaDTO;
import com.matus.cursomc.repositorys.CategoriaRepository;
import com.matus.cursomc.services.exception.DataIntegrityException;
import com.matus.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	//---------A classe SERVICE vai acessar o classe de REPOSITORY (private CategoriaRepository repo;)---------
	@Autowired// repo é automaticamente instaciando pelo Spring
	private CategoriaRepository repo;
	
	//Criar uma operacao q busca uma categoria por codigo
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: "+id+", Tipo: "+ Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null); // Garantir q o ID é null para criar um novo obj
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		//Quando o ID é null ele insere, quando ele existir ele atualiza o obj
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é posivel excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	// Vai pegar os dados por parte, ex: de 20 em 20, para diminuir o uso do banco
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	// Apartir de um obj categoria vai contruir um obj DTO
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
}
