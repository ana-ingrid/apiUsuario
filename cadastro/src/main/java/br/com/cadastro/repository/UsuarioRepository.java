package br.com.cadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cadastro.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{

	
	
}
