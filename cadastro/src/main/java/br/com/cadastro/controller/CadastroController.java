package br.com.cadastro.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastro.exception.UsuarioNaoEncontradoException;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.service.CadastroService;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

	@Autowired
	private CadastroService service;
	
	@GetMapping
	public ResponseEntity<Usuario> consultaUsuario(@RequestParam String cpf) throws Exception {
		Usuario use = service.consultaUsuario(cpf);
		return ResponseEntity.status(200).body(use);
	}
	
	
	@GetMapping("/lista")
	public ResponseEntity<List<Usuario>> listaUsuario() {	
		return ResponseEntity.status(200).body(service.listaUsuario());
	}
	
	@PostMapping
	public ResponseEntity<Usuario> cadastraUsuario(@RequestBody Usuario user) {
		return ResponseEntity.status(201).body(service.cadastraUsuario(user));
	}
	
	@PutMapping("/altera")
	public ResponseEntity<Usuario> alteraUsuario(@RequestBody Usuario user) throws UsuarioNaoEncontradoException {
		service.alteraUsuario(user);
		return ResponseEntity.status(200).build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletaUsuario(String cpf) throws UsuarioNaoEncontradoException {
		service.deletaUsuario(cpf);
		return ResponseEntity.status(204).build();			 
		}
	
}
