package com.matus.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.matus.cursomc.domain.Categoria;

@RestController // Criou umas classe Rest
@RequestMapping(value="/categorias")// Vai responder por esse end-point: /categirias
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET) //se for colocar algo POST, deletar DELETE, etc
	public List<Categoria> listar() {
		
		Categoria cat1 = new Categoria(1, "Informatica");
		Categoria cat2 = new Categoria(2, "Escritorio");
		
		List<Categoria> lista = new ArrayList<>();
		lista.add(cat1);
		lista.add(cat2);
		
		return lista;
	}
}
