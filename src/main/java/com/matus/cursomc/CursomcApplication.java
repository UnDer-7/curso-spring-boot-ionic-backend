package com.matus.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.matus.cursomc.Repositorys.CategoriaRepository;
import com.matus.cursomc.domain.Categoria;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	// Cria uma ligacao com a classe q cuida do BD, Repository
	@Autowired
	private CategoriaRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	// Toda vez o projeto for iniciado ela vai gerar esses obj.
	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		// Salva os obj no banco
		repo.saveAll(Arrays.asList(cat1,cat2));
	}
}
