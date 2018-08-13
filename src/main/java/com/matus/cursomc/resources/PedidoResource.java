package com.matus.cursomc.resources;

import com.matus.cursomc.domain.Categoria;
import com.matus.cursomc.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.matus.cursomc.domain.Pedido;
import com.matus.cursomc.services.PedidoService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController // Criou umas classe Rest
@RequestMapping(value="/pedidos")// O URL q vai ser usado /categirias
public class PedidoResource {
	
	//---------A classe REST vai acessar o servico (private PedidoService service)---------
	@Autowired
	private PedidoService service;
	
	//value="/{id}" ==> Agoro o url vai ser: /categirias/IdQueForPassadoParaBuscar
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //se for colocar algo POST, deletar DELETE, etc
	
	// ResponseEntity<?> ==> Coisa do Spring, armazena varias informacoes de uma respota HTTP para o servico REST
	// @PathVariable ==> Fala q o Integer id vai ser id da URL (value="/{id}") 
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	// @RequestBody ==> Vai criar o Objeto apartir do JSon.
	// @Valid ==> Valida o campo
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){
		// Recebe um CategoriaDTO é pasa para Categoria
		obj = service.insert(obj);
		// uri ==> Vai criar a url pra acessar a categora
		// fromCurrentRequest() ==> Pega a url usada como padrao, no caso: http://localhost:8081/pedidos/
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
