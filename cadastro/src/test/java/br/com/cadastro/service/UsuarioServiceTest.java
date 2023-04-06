package br.com.cadastro.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.cadastro.dto.AlteraUsuarioDto;
import br.com.cadastro.dto.BuscaAvancadaDto;
import br.com.cadastro.dto.CadastraUsuarioDto;
import br.com.cadastro.dto.EnderecoDto;
import br.com.cadastro.exception.UsuarioExistenteException;
import br.com.cadastro.exception.UsuarioNaoEncontradoException;
import br.com.cadastro.model.Endereco;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.repository.UsuarioRepository;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

	@InjectMocks
	private UsuarioService service;

	@Mock
	private UsuarioRepository repository;

	@Mock
	private ModelMapper mapper;

	@Test
	public void criaUsuarioSucesso() throws UsuarioExistenteException {
		
		CadastraUsuarioDto dto = new CadastraUsuarioDto();
		dto.setNome("testNome");
		dto.setCpf("12345678910");
		dto.setNascimento(new Date());
		dto.setSexo("testSexo");

		Endereco endereco = new Endereco();
		endereco.setLogradouro("testLogradouro");
		endereco.setNumero(1);
		endereco.setCidade("testCidade");
		endereco.setUf("testUf");
		
		Usuario user = new Usuario();
		user.setNome(dto.getNome());
		user.setCpf(dto.getCpf());
		user.setSexo(dto.getSexo());
		user.setEndereco(endereco);

		Mockito.when(repository.findById(dto.getCpf())).thenReturn(Optional.empty());
		Mockito.when(mapper.map(dto, Usuario.class)).thenReturn(user);
		Mockito.when(repository.save(user)).thenReturn(user);

		Usuario user1 = service.cadastraUsuario(dto);
		assertNotNull(user1);
	}

	@Test
	public void consultaUsuarioSucesso() throws UsuarioNaoEncontradoException {

		Usuario user = new Usuario();
		user.setNome("testNome");
		user.setCpf("12345678910");
		user.setSexo("testSexo");
		user.setNascimento(new Date());
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("testLogradouro");
		endereco.setNumero(1);
		endereco.setCidade("testCidade");
		endereco.setUf("testUf");

		user.setEndereco(endereco);
		
		Mockito.when(repository.findById(user.getCpf())).thenReturn(Optional.of(user));
		Usuario usuarioPorId = service.consultaUsuarioPorId(user.getCpf());
		assertNotNull(usuarioPorId);
	}

	@Test
	public void consultaUsuariosSucesso() throws UsuarioNaoEncontradoException {

//		User1
		Usuario user1 = new Usuario();
		user1.setNome("testNome");
		user1.setCpf("12345678910");
		user1.setSexo("testSexo");
		user1.setNascimento(new Date());

		Endereco endereco1 = new Endereco();
		endereco1.setLogradouro("testeLogradouro");
		endereco1.setNumero(1);
		endereco1.setCidade("testeCidade");
		endereco1.setUf("testeUf");

		user1.setEndereco(endereco1);

//		User2
		Usuario user2 = new Usuario();
		user2.setNome("test");
		user2.setCpf("12345678911");
		user2.setSexo("testSexo");
		user2.setNascimento(new Date());

		Endereco endereco2 = new Endereco();
		endereco2.setLogradouro("testeLogradouro");
		endereco2.setNumero(1);
		endereco2.setCidade("testeCidade");
		endereco2.setUf("testeUf");

		user2.setEndereco(endereco2);
				
		List<Usuario> list = new ArrayList<Usuario>();
		list.add(user1);
		list.add(user2);
		Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn( new PageImpl<Usuario>(list));
		
		Page<Usuario> pageable = service.ConsultaUsuarios(Pageable.ofSize(2));
		assertNotNull(pageable);
		assertEquals(pageable.getContent().size(), list.size());
		}

	@Test
	public void alteraUsuarioSucesso() throws UsuarioNaoEncontradoException, UsuarioExistenteException {

		Usuario user = new Usuario();
		user.setNome("testNome");
		user.setCpf("12345678910");
		user.setSexo("testSexo");
		user.setNascimento(new Date());

		Endereco endereco1 = new Endereco();
		endereco1.setLogradouro("testeLogradouro");
		endereco1.setNumero(1);
		endereco1.setCidade("testeCidade");
		endereco1.setUf("testeUf");

		user.setEndereco(endereco1);

		AlteraUsuarioDto dto = new AlteraUsuarioDto();
		dto.setNome("test");
		dto.setNascimento(new Date());
		dto.setSexo("test");

		Mockito.when(repository.findById(user.getCpf())).thenReturn(Optional.of(user));
		Mockito.doNothing().when(mapper).map(dto, user);
		Mockito.when(repository.save(user)).thenReturn(user);

		Usuario alteracao = service.alteraUsuario(dto, user.getCpf());
		assertNotNull(alteracao);
	}

	@Test
	public void deletaUsuarioSucesso() throws UsuarioNaoEncontradoException {

		Usuario user = new Usuario();
		user.setNome("test");
		user.setCpf("12345678910");
		user.setSexo("testSexo");
		user.setNascimento(new Date());

		Endereco endereco = new Endereco();
		endereco.setLogradouro("testLogradouro");
		endereco.setNumero(1);
		endereco.setCidade("testCidade");
		endereco.setUf("testUf");

		user.setEndereco(endereco);

		Mockito.when(repository.findById(user.getCpf())).thenReturn(Optional.of(user));
		Mockito.doNothing().when(repository).delete(user);
		
		service.deletaUsuario(user.getCpf());
		Mockito.verify(repository, Mockito.times(1)).delete(user);		
	}

	@Test
	public void buscaAvancada() throws UsuarioNaoEncontradoException {
		
		BuscaAvancadaDto dto = new BuscaAvancadaDto();
		dto.setCpf("12345678910");
		dto.setNascimento(new Date());
		dto.setSexo("testSexo");
		dto.setCidade("testCidade");
		dto.setUf("testUf");
		
		Usuario user = new Usuario();
		user.setCpf(dto.getCpf());
		user.setSexo(dto.getSexo());
		user.setNascimento(dto.getNascimento());
		Endereco endereco = new Endereco();
		endereco.setCidade(dto.getCidade());
		endereco.setUf(dto.getUf());
		user.setEndereco(endereco);
	
		Example<Usuario> example = Example.of(user);
		List<Usuario> buscaAvancada = service.buscaAvancadaUsuario(dto);
		assertNotNull(buscaAvancada);
	}
	
}
