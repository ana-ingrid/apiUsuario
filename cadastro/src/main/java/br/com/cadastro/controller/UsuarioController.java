package br.com.cadastro.controller;


import java.util.List;

import javax.validation.Valid;

import br.com.cadastro.dto.EnderecoDTO;
import br.com.cadastro.service.CepService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastro.dto.AlteraUsuarioDTO;
import br.com.cadastro.dto.BuscaAvancadaDTO;
import br.com.cadastro.dto.CadastraUsuarioDTO;
import br.com.cadastro.exception.RecursoExistenteException;
import br.com.cadastro.exception.RecursoNaoEncontradoException;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
 public class UsuarioController implements ResponseEntityStatus {

	private final UsuarioService serviceUsuario;

	private final CepService serviceCep;

	public UsuarioController(CepService serviceCep, UsuarioService serviceUsuario) {
		this.serviceCep = serviceCep;
		this.serviceUsuario = serviceUsuario;
	}

	@ApiOperation("Consulta Usuário por CPF")
	@GetMapping("/{cpf}")
	public ResponseEntity<Usuario> consultaUsuario(@PathVariable String cpf) throws RecursoNaoEncontradoException {
		return response(HttpStatus.OK, serviceUsuario.consultaUsuarioPorId(cpf, true));
	}

	@ApiOperation("Consulta de Todos os Usuários com Paginação")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Sem Registros de Usuários") })
	@GetMapping
	public ResponseEntity<Page<Usuario>> consultaUsuarios(Pageable pageable) {
		return response(HttpStatus.OK, serviceUsuario.consultaUsuariosPaginada(pageable));
	}

	@ApiOperation("Cadastro dos Dados do Usuário")
	@PostMapping
	public ResponseEntity<Usuario> cadastraUsuario(@Valid @RequestBody CadastraUsuarioDTO user) throws RecursoExistenteException {
		return response(HttpStatus.CREATED, serviceUsuario.cadastraUsuario(user));
	}

	@ApiOperation("Alteração dos Dados do Usuário")
	@PutMapping(value = "/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> alteraUsuario(@PathVariable String cpf, @Valid @RequestBody AlteraUsuarioDTO userDto) throws RecursoNaoEncontradoException {
		return response(HttpStatus.CREATED, serviceUsuario.alteraUsuario(userDto, cpf));
	}

	@ApiOperation("Exclusão dos Dados do Usuário")
	@DeleteMapping(value = "/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deletaUsuario(@PathVariable String cpf) throws RecursoNaoEncontradoException {
		serviceUsuario.deletaUsuario(cpf);
		return responseNoContent(HttpStatus.NO_CONTENT);
		}

	@ApiOperation("Consulta Avançada por Dados")
	@GetMapping(value = "/buscaAvancada", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usuario>> buscaAvancadaUsuario(BuscaAvancadaDTO buscaAvancadaDto) throws RecursoNaoEncontradoException {
		return response(HttpStatus.OK, serviceUsuario.buscaAvancadaUsuario(buscaAvancadaDto));
	}
	@ApiOperation("Validação do Cep e retorno dos Dados")
	@GetMapping(value = "/cep/{cep}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EnderecoDTO validacaoCep  (@PathVariable("cep") String cep) {
		return response(HttpStatus.OK, serviceCep.dadosCep(cep)).getBody();
	}

}
