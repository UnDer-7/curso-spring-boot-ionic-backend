package com.matus.cursomc.repositorys;

import com.matus.cursomc.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matus.cursomc.domain.Pedido;
import org.springframework.transaction.annotation.Transactional;

// Classe responsavel por acessar o BD e fazer as consultas(salvar, deletar, etc)
//e acessar os dados de uma tabela
@Repository
// JpaRepository<A, B> ==> A: Vai acessar os dados com base no tipo q foi passado
// B: Tipo de atributo identificador do obj, no caso é o Integer q é o ID
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

    @Transactional(readOnly = true)
    Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
}
