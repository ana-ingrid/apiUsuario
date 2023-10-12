package br.com.cadastro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cadastro.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{

	
	
}
