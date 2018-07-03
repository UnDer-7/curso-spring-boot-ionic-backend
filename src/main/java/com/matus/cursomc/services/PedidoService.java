package com.matus.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matus.cursomc.domain.Pedido;
import com.matus.cursomc.repositorys.PedidoRepository;
import com.matus.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	//---------A classe SERVICE vai acessar o classe de REPOSITORY (private PedidoRepository repo;)---------
	@Autowired// repo é automaticamente instaciando pelo Spring
	private PedidoRepository repo;
	
	//Criar uma operacao q busca uma categoria por codigo
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: "+id+", Tipo: "+ Pedido.class.getName()));
	}
}
