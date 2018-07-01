package com.matus.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.matus.cursomc.Repositorys.CategoriaRepository;
import com.matus.cursomc.Repositorys.ProdutoRepository;
import com.matus.cursomc.domain.Categoria;
import com.matus.cursomc.domain.Produto;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	// Cria uma ligacao com a classe q cuida do BD, Repository
	@Autowired
	private CategoriaRepository catRepo;
	@Autowired
	private ProdutoRepository proRepo;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	// Toda vez o projeto for iniciado ela vai gerar esses obj.
	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		// No diagrama: P1, P2 e P3 estao associados a cat1
		// ja na cat2 so tem o P2 associados
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));		
		
		// Salva os obj no banco
		catRepo.saveAll(Arrays.asList(cat1,cat2));
		proRepo.saveAll(Arrays.asList(p1,p2,p3));
	}
}
