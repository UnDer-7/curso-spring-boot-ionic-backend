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
public class CursomcApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
