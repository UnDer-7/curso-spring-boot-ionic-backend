package com.matus.cursomc.repositorys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.matus.cursomc.domain.Categoria;
import com.matus.cursomc.domain.Produto;

// Classe responsável por acessar o BD e fazer as consultas(salvar, deletar, etc)
//e acessar os dados de uma tabela
@Repository
// JpaRepository<A, B> ==> A: Vai acessar os dados com base no tipo q foi passado
// B: Tipo de atributo identificador do obj, no caso é o Integer q é o ID
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	//---------A classe REPOSITORY vai acessar o BD---------
	
	//@Query ==> Deixar criar a apropria JPQL.
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	//@Param ==> Fazer a ligação entre o %:nome% e o String nome
	Page<Produto> search(@Param("nome") String nome, @Param("categorias")List<Categoria> categorias, Pageable pageRequest);
	
	//Faz a mesa coisa que o método acima
	//findDistinctBy""O primeiro argumento, no caso nome
	//Containing ==> Aplica o Like na procura
//	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
