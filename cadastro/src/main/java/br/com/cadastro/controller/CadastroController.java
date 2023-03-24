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

import br.com.cadastro.model.Usuario;
import br.com.cadastro.service.CadastroService;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

	@Autowired
	private CadastroService service;
	
	@GetMapping
	public ResponseEntity<Usuario> consultaUsuario(@RequestParam String cpf) {
		Usuario use = service.consultaUsuario(cpf);
		if (use.getCpf() != null) {
		 return ResponseEntity.status(200).body(use);
		} 
		 return ResponseEntity.notFound().build();
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
	public ResponseEntity<Usuario> alteraUsuario(@RequestBody Usuario use) {
		Usuario user = service.alteraUsuario(use);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.status(200).body(user);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletaUsuario(String cpf) {
		 boolean use= service.deletaUsuario(cpf);
		ResponseEntity.status(204).body(use);
		return ResponseEntity.notFound().build();
	}
	
}
