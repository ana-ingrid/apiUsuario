package br.com.cadastro.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import br.com.cadastro.dto.AlteraUsuarioDto;
import br.com.cadastro.dto.CadastraUsuarioDto;
import br.com.cadastro.exception.UsuarioExistenteException;
import br.com.cadastro.exception.UsuarioNaoEncontradoException;
import br.com.cadastro.model.Endereco;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario consultaUsuarioPorId(String cpf) throws UsuarioNaoEncontradoException {
		Optional<Usuario> optional = repository.findById(cpf);
		return optional.orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado!"));
	}
	
	
	public Usuario cadastraUsuario( CadastraUsuarioDto dto) throws UsuarioExistenteException {	
		if (repository.findById(dto.getCpf()).isPresent()) {
			throw new UsuarioExistenteException("Usuário já existe");
		}	
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		return repository.save(usuario);
	}
	
	public Page<Usuario> ConsultaUsuarios(Pageable pageable) {
		return repository.findAll(pageable);
	} 
	
	
	public Usuario alteraUsuario(AlteraUsuarioDto dto, String cpf) throws UsuarioNaoEncontradoException {
		Usuario user = consultaUsuarioPorId(cpf);
		modelMapper.map(dto, user);
		return repository.save(user);
	}

	public void deletaUsuario(String cpf) throws UsuarioNaoEncontradoException {
		Usuario user = consultaUsuarioPorId(cpf);
		repository.delete(user);
	}

}