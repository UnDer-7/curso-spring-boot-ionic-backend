package com.matus.cursomc.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController // Criou umas classe Rest
@RequestMapping(value="/categorias")// Vai responder por esse end-point: /categirias
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET) //se for colocar algo POST, deletar DELETE, etc
	public String listar() {
		return "Rest esta funcinando";
	}
}
