package com.matus.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matus.cursomc.domain.Categoria;
import com.matus.cursomc.dto.CategoriaDTO;
import com.matus.cursomc.services.CategoriaService;

@RestController // Criou umas classe Rest
@RequestMapping(value="/categorias")// O URL q vai ser usado /categirias
public class CategoriaResource {
	
	//---------A classe REST vai acessar o servico (private CategoriaService service)---------
	@Autowired
	private CategoriaService service;
	
	//value="/{id}" ==> Agoro o url vai ser: /categirias/IdQueForPassadoParaBuscar
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //se for colocar algo POST, deletar DELETE, etc
	
	// ResponseEntity<?> ==> Coisa do Spring, armazena varias informacoes de uma respota HTTP para o servico REST
	// @PathVariable ==> Fala q o Integer id vai ser id da URL (value="/{id}") 
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);	
	}
	
	@RequestMapping(method = RequestMethod.POST)
	// @RequestBody ==> Vai criar o Objeto apartir do JSon.
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = service.insert(obj);
		// uri ==> Vai criar a url pra acessar a categora
		// fromCurrentRequest() ==> Pega a url usada como padrao, no caso: http://localhost:8081/pedidos/
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	//Update tem q pegar o obj pelo @RequestBody, e depois inserir o dado no obj pelo @PathVariable
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//Busca todos as categorias
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList()); // map() ==> Vai fazer uma opecao para cada elemento da lista
		return ResponseEntity.ok().body(listDTO);	
	}
}










