package com.matus.cursomc.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matus.cursomc.domain.Pedido;

// Classe responsavel por acessar o BD e fazer as consultas(salvar, deletar, etc)
//e acessar os dados de uma tabela
@Repository
// JpaRepository<A, B> ==> A: Vai acessar os dados com base no tipo q foi passado
// B: Tipo de atributo identificador do obj, no caso é o Integer q é o ID
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	//---------A classe REPOSITORY vai acessar o BD---------
}
