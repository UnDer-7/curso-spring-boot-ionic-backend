package com.matus.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.matus.cursomc.domain.enums.Perfil;
import com.matus.cursomc.security.UserSS;
import com.matus.cursomc.services.exception.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matus.cursomc.domain.Cidade;
import com.matus.cursomc.domain.Cliente;
import com.matus.cursomc.domain.Endereco;
import com.matus.cursomc.domain.enums.TipoCliente;
import com.matus.cursomc.dto.ClienteDTO;
import com.matus.cursomc.dto.ClienteNewDTO;
import com.matus.cursomc.repositorys.ClienteRepository;
import com.matus.cursomc.repositorys.EnderecoRepository;
import com.matus.cursomc.services.exception.DataIntegrityException;
import com.matus.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteService {

	// ---------A classe SERVICE vai acessar o classe de REPOSITORY (private
	// ClienteRepository repo;)---------
	@Autowired // repo é automaticamente instaciando pelo Spring
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
    private String prefix;

	// Criar uma operacao q busca uma categoria por codigo
	public Cliente find(Integer id) {

	    //Pegar usuario logado;
	    UserSS user = UserService.authnticated();
	    if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null); // Garantir q o ID é null para criar um novo obj
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		// newObj vai buscar todos os dados no BD
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		// Quando o ID é null ele insere, quando ele existir ele atualiza o obj
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é posivel excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	// Vai pegar os dados por parte, ex: de 20 em 20, para diminuir o uso do banco
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	// Apartir de um obj categoria vai contruir um obj DTO
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}

	// insere
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		// O endereço conhece seu cliente pelo "cli"
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid);
		// O cliente agora conhece o seu endereço
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		// Os dados no BD foi atualizado com os novos dados q foram passados no obj,
		// pega o novo obj q fizemeos apartir do BD(newObj) e atualiza com os novos dado
		// q foi passado na requisição(obj )
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public URI uploadProfilePicture(MultipartFile multipartFile){
        UserSS user = UserService.authnticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        String fileName = prefix + user.getId() + ".jpg";
        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }
}
