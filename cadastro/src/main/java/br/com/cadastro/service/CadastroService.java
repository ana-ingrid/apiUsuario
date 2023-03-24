package br.com.cadastro.service;

import java.rmi.server.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cadastro.model.Usuario;
import br.com.cadastro.repository.UsuarioRepository;

@Service
public class CadastroService {

	@Autowired
	private UsuarioRepository repository;
	
	public Usuario consultaUsuario(String cpf) {	
		 Optional<Usuario> optional = repository.findById(cpf);
		 return optional.orElse(new Usuario());
	}
	
	public Usuario cadastraUsuario( Usuario user) {	
		return repository.save(user);
	}
	
	public List<Usuario> listaUsuario() {
		
		 List<Usuario> lista = repository.findAll();
		 return lista;
	}
	
	
	public Usuario alteraUsuario(Usuario use) {

		if(repository.findById(use.getCpf()).isPresent()) {
			return repository.save(use);	
		}
		return null;
	}

	public boolean deletaUsuario(String cpf) {
		if(repository.findById(cpf).isPresent()) {
			repository.deleteById(cpf);
			return true;
		}
		
		return false;
	}
	
}
