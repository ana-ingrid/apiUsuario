package br.com.cadastro.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastro.dto.AlteraUsuarioDto;
import br.com.cadastro.dto.CadastraUsuarioDto;
import br.com.cadastro.exception.UsuarioExistenteException;
import br.com.cadastro.exception.UsuarioNaoEncontradoException;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
 public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@GetMapping("/{cpf}")
	public ResponseEntity<Usuario> consultaUsuario(@PathVariable String cpf) throws UsuarioNaoEncontradoException {
		return ResponseEntity.status(200).body(service.consultaUsuarioPorId(cpf));
	}
	
	
	@GetMapping
	public ResponseEntity<Page<Usuario>> ConsultaUsuarios(Pageable pageable) {	
		return ResponseEntity.status(200).body(service.ConsultaUsuarios(pageable));
	}
	
	@PostMapping
	public ResponseEntity<Usuario> cadastraUsuario(@Valid @RequestBody CadastraUsuarioDto user) throws  UsuarioExistenteException {
		return ResponseEntity.status(201).body(service.cadastraUsuario(user));
	}
	
	@PutMapping("/{cpf}")
	public ResponseEntity<Usuario> alteraUsuario(@PathVariable String cpf, @Valid @RequestBody AlteraUsuarioDto userDto) throws UsuarioNaoEncontradoException {
		return ResponseEntity.status(200).body(service.alteraUsuario(userDto, cpf));
	}
	
	@DeleteMapping("/{cpf}")
	public ResponseEntity<Void> deletaUsuario(@PathVariable String cpf) throws UsuarioNaoEncontradoException {
		service.deletaUsuario(cpf);
		return ResponseEntity.status(204).build();			 
		}
}
