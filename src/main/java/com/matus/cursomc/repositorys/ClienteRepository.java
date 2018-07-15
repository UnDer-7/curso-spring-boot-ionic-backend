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
	
	//Transactional ==> Vai precisa ser envolvida com uma transação de BD
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	//findBy ==> Parada do SpringData, faz uma busca no BD por Email
	//findBy ==> Um padrao de nome de metodo do Spring q automaticamente faz uma busca baseada no parametro do metodo
	// so procurar por 'query method spring data' no google
	Cliente findByEmail(String email);
}
