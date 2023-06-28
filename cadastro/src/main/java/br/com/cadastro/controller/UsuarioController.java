package br.com.cadastro.controller;


import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import br.com.cadastro.dto.BuscaAvancadaDto;
import br.com.cadastro.dto.CadastraUsuarioDto;
import br.com.cadastro.exception.RecursoExistenteException;
import br.com.cadastro.exception.RecursoNaoEncontradoException;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
 public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@ApiOperation("Consulta Usuário por CPF")
	@GetMapping("/{cpf}")
	public ResponseEntity<Usuario> consultaUsuario(@PathVariable String cpf) throws RecursoNaoEncontradoException {
		return ResponseEntity.status(200).body(service.consultaUsuarioPorId(cpf, true));
	}

	@ApiOperation("Consulta de Todos os Usuários com Paginação")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Sem Registros de Usuários") })
	@GetMapping
	public ResponseEntity<Page<Usuario>> consultaUsuarios(Pageable pageable) {
		return ResponseEntity.status(200).body(service.consultaUsuariosPaginada(pageable));
	}

	@ApiOperation("Cadastro dos Dados do Usuário")
	@PostMapping
	public ResponseEntity<Usuario> cadastraUsuario(@Valid @RequestBody CadastraUsuarioDto user) throws RecursoExistenteException {
		return ResponseEntity.status(201).body(service.cadastraUsuario(user));
	}

	@ApiOperation("Alteração dos Dados do Usuário")
	@PutMapping("/{cpf}")
	public ResponseEntity<Usuario> alteraUsuario(@PathVariable String cpf, @Valid @RequestBody AlteraUsuarioDto userDto) throws RecursoNaoEncontradoException {
		return ResponseEntity.status(200).body(service.alteraUsuario(userDto, cpf));
	}

	@ApiOperation("Exclusão dos Dados do Usuário")
	@DeleteMapping("/{cpf}")
	public ResponseEntity<Void> deletaUsuario(@PathVariable String cpf) throws RecursoNaoEncontradoException {
		service.deletaUsuario(cpf);
		return ResponseEntity.status(204).build();			 
		}

	@ApiOperation("Consulta Avançada por Dados")
	@GetMapping("/buscaAvancada")
	public ResponseEntity<List<Usuario>> buscaAvancadaUsuario(BuscaAvancadaDto buscaAvancadaDto) throws RecursoNaoEncontradoException {
		return ResponseEntity.status(200).body(service.buscaAvancadaUsuario(buscaAvancadaDto));
	}
}
