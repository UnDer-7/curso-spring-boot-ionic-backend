package com.matus.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matus.cursomc.domain.Cliente;
import com.matus.cursomc.repositorys.ClienteRepository;
import com.matus.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//---------A classe SERVICE vai acessar o classe de REPOSITORY (private ClienteRepository repo;)---------
	@Autowired// repo é automaticamente instaciando pelo Spring
	private ClienteRepository repo;
	
	//Criar uma operacao q busca uma categoria por codigo
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: "+id+", Tipo: "+ Cliente.class.getName()));
	}
}
