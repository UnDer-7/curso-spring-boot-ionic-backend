package com.matus.cursomc.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matus.cursomc.domain.Cliente;

// Classe responsavel por acessar o BD e fazer as consultas(salvar, deletar, etc)
//e acessar os dados de uma tabela
@Repository
// JpaRepository<A, B> ==> A: Vai acessar os dados com base no tipo q foi passado
// B: Tipo de atributo identificador do obj, no caso é o Integer q é o ID
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	//---------A classe REPOSITORY vai acessar o BD---------
	
	//findBy ==> Parada do SpringData, faz uma busca no BD por Email
	//Transactional ==> Vai precisa ser envolvida com uma transação de BD
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	Cliente findByEmail(String email);
}
