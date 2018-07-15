package com.matus.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matus.cursomc.domain.Produto;
import com.matus.cursomc.dto.ProdutoDTO;
import com.matus.cursomc.resources.utils.URL;
import com.matus.cursomc.services.ProdutoService;

@RestController // Criou umas classe Rest
@RequestMapping(value="/produtos")// O URL q vai ser usado /categirias
public class ProdutoResource {
	
	//---------A classe REST vai acessar o servico (private ProdutoService service)---------
	@Autowired
	private ProdutoService service;
	
	//value="/{id}" ==> Agoro o url vai ser: /categirias/IdQueForPassadoParaBuscar
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //se for colocar algo POST, deletar DELETE, etc
	
	// ResponseEntity<?> ==> Coisa do Spring, armazena varias informacoes de uma respota HTTP para o servico REST
	// @PathVariable ==> Fala q o Integer id vai ser id da URL (value="/{id}") 
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	//Metodo GET é usado pra recuperar dados
	//URL pra pesquisar Produto: http://localhost:8081/produtos/?nome=or&categorias=1,4
	//?nome= ==> Faz um like, procura por todo o BD(mas só procura nas categorias passadas) palavras q possuam o caractere passado. 
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
	//@RequestParam ==> Para q vire opcional na URL, ex: categorias/page?page=0&linesPerPage = 20 etc			
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		//Quando uma String com espaços em branco é passado na URL ela pode vir "estranha", Ex: "TV Led", vai ficar "TV%20Led". URLDecoder trata isso
		String nomeDecoder = URL.decodeParam(nome);
		//URL.decodeIntList ==> Como os parâmetros de URL são String é necessário converte-los para Integer
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoder, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDTO);	
	}
}
