package com.matus.cursomc.services;

import java.util.Date;
import java.util.Optional;

import com.matus.cursomc.domain.ItemPedido;
import com.matus.cursomc.domain.PagamentoComBoleto;
import com.matus.cursomc.domain.enums.EstadoPagamento;
import com.matus.cursomc.repositorys.ClienteRepository;
import com.matus.cursomc.repositorys.ItemPedidoRepository;
import com.matus.cursomc.repositorys.PagamentoRepository;
import com.matus.cursomc.repositorys.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matus.cursomc.domain.Pedido;
import com.matus.cursomc.repositorys.PedidoRepository;
import com.matus.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;

@Service
public class PedidoService {
	
	//---------A classe SERVICE vai acessar o classe de REPOSITORY (private PedidoRepository repo;)---------
	@Autowired// repo é automaticamente instaciando pelo Spring
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
    private PagamentoRepository pagamentoRepository;

	@Autowired
    private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	//Criar uma operacao q busca uma categoria por codigo
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: "+id+", Tipo: "+ Pedido.class.getName()));
	}

	@Transactional
    public Pedido insert(Pedido obj){
	    obj.setId(null);
	    obj.setInstante(new Date());
	    obj.setCliente(clienteService.find(obj.getCliente().getId()));
	    obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
	    //Associando Pagamento com Pedido
	    obj.getPagamento().setPedido(obj);
	    if(obj.getPagamento() instanceof PagamentoComBoleto){
	        PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
	        boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
    obj = repo.save(obj);
	pagamentoRepository.save(obj.getPagamento());
	for(ItemPedido ip: obj.getItens()){
	    ip.setDesconto(0.0);
	    ip.setProduto(produtoService.find(ip.getProduto().getId()));
        ip.setPreco(ip.getProduto().getPreco());
        ip.setPedido(obj);
	}
	itemPedidoRepository.saveAll(obj.getItens());
	emailService.sendOrderConfirmationHtmlEmail(obj);
	return obj;
	}
}