	package com.matus.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.matus.cursomc.domain.Categoria;
import com.matus.cursomc.domain.Cidade;
import com.matus.cursomc.domain.Cliente;
import com.matus.cursomc.domain.Endereco;
import com.matus.cursomc.domain.Estado;
import com.matus.cursomc.domain.ItemPedido;
import com.matus.cursomc.domain.Pagamento;
import com.matus.cursomc.domain.PagamentoComBoleto;
import com.matus.cursomc.domain.PagamentoComCartao;
import com.matus.cursomc.domain.Pedido;
import com.matus.cursomc.domain.Produto;
import com.matus.cursomc.domain.enums.EstadoPagamento;
import com.matus.cursomc.domain.enums.TipoCliente;
import com.matus.cursomc.repositorys.CategoriaRepository;
import com.matus.cursomc.repositorys.CidadeRepository;
import com.matus.cursomc.repositorys.ClienteRepository;
import com.matus.cursomc.repositorys.EnderecoRepository;
import com.matus.cursomc.repositorys.EstadoRepository;
import com.matus.cursomc.repositorys.ItemPedidoRepository;
import com.matus.cursomc.repositorys.PagamentoRepository;
import com.matus.cursomc.repositorys.PedidoRepository;
import com.matus.cursomc.repositorys.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	// Cria uma ligacao com a classe q cuida do BD, Repository
	@Autowired
	private CategoriaRepository catRepo;
	@Autowired
	private ProdutoRepository proRepo;
	@Autowired
	private EstadoRepository estRepo;
	@Autowired
	private CidadeRepository cidRepo;
	@Autowired
	private ClienteRepository cliRepo;
	@Autowired
	private EnderecoRepository endRepo;
	@Autowired
	private PedidoRepository pedRepo;
	@Autowired
	private PagamentoRepository pagRepo;
	@Autowired
	private ItemPedidoRepository itemPedRepo;
	
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

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estRepo.saveAll(Arrays.asList(est1, est2));
		cidRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva","maria@gmail.com","46223096155", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("33332222","99995555"));
		
		Endereco e1 = new Endereco(null, "Rua Flores","300", "Apto 303", "Jardins", "38222", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos","105", "sala 800", "Centro", "5555", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		cliRepo.saveAll(Arrays.asList(cli1));
		endRepo.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedRepo.saveAll(Arrays.asList(ped1, ped2));
		pagRepo.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedRepo.saveAll(Arrays.asList(ip1,ip2,ip3));
	}
}
