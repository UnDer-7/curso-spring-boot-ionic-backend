package com.matus.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.matus.cursomc.domain.Cliente;
import com.matus.cursomc.dto.ClienteDTO;
import com.matus.cursomc.repositorys.ClienteRepository;
import com.matus.cursomc.services.exception.DataIntegrityException;
import com.matus.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//---------A classe SERVICE vai acessar o classe de REPOSITORY (private ClienteRepository repo;)---------
	@Autowired// repo é automaticamente instaciando pelo Spring
	private ClienteRepository repo;
	
	//Criar uma operacao q busca uma categoria por codigo
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: "+id+", Tipo: "+ Cliente.class.getName()));
	}
	
	public Cliente update(Cliente obj) {
		// newObj vai buscar todos os dados no BD
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		//Quando o ID é null ele insere, quando ele existir ele atualiza o obj
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é posivel excluir porque há entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	// Vai pegar os dados por parte, ex: de 20 em 20, para diminuir o uso do banco
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	// Apartir de um obj categoria vai contruir um obj DTO
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		// Os dados no BD foi atualizado com os novos dados q foram passados no obj, 
		//pega o novo obj q fizemeos apartir do BD(newObj) e atualiza com os novos dado q foi passado na requisição(obj ) 
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
