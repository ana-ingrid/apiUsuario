package br.com.cadastro.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cadastro.exception.UsuarioNaoEncontradoException;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.repository.UsuarioRepository;

@Service
public class CadastroService {

	@Autowired
	private UsuarioRepository repository;
	
	public Optional<Usuario> consultaUsuarioPorId(String cpf) {
		Optional<Usuario> optional = repository.findById(cpf);
		return optional;
	}
	
	
	public Usuario consultaUsuario(String cpf) throws Exception {	
		 return consultaUsuarioPorId(cpf).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado!"));
	}
	
	public Usuario cadastraUsuario( Usuario user) {	
		return repository.save(user);
	}
	
	public List<Usuario> listaUsuario() {
		 List<Usuario> lista = repository.findAll();
		 return lista;
	}
	
	
	public Usuario alteraUsuario(Usuario use) throws UsuarioNaoEncontradoException {
		Usuario user = consultaUsuarioPorId(use.getCpf()).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado!"));
		return repository.save(user);
	}

	public void deletaUsuario(String cpf) throws UsuarioNaoEncontradoException {
		Usuario user = consultaUsuarioPorId(cpf).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado!"));
		repository.delete(user);
	}

}