package br.com.cadastro.service;

import Util.MockJson;
import br.com.cadastro.config.MensagemValidacao;
import br.com.cadastro.dto.AlteraUsuarioDTO;
import br.com.cadastro.dto.BuscaAvancadaDTO;
import br.com.cadastro.dto.CadastraUsuarioDTO;
import br.com.cadastro.exception.FiltroException;
import br.com.cadastro.exception.RecursoExistenteException;
import br.com.cadastro.exception.RecursoNaoEncontradoException;
import br.com.cadastro.model.Endereco;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.repository.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static Util.MockJson.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UsuarioServiceTest {


	@InjectMocks
	private UsuarioService service;

	@Mock
	private UsuarioRepository repository;

	@Mock
	private ModelMapper mapper;

	@Test
	public void cadastraUsuarioSucesso() throws RecursoExistenteException {
		CadastraUsuarioDTO usuarioDto = MockJson.getMockCadastraUsuarioDto();
		Usuario usuario = getMockUsuario();

		when(repository.findById(usuarioDto.getCpf())).thenReturn(Optional.empty());
		when(mapper.map(any(), any())).thenReturn(usuario);
		when(repository.save(usuario)).thenReturn(usuario);

		Usuario metodoCadastra = service.cadastraUsuario(usuarioDto);
		assertEquals(usuario.getCpf(), metodoCadastra.getCpf());
	}
	@Test
	public void cadastraUsuarioException() {
		CadastraUsuarioDTO usuarioDto = MockJson.getMockCadastraUsuarioDto();
		Usuario usuario = getMockUsuario();
		when(repository.findById(usuarioDto.getCpf())).thenReturn(Optional.of(usuario));

		assertThatThrownBy(() -> service.cadastraUsuario(usuarioDto)).isInstanceOf(RecursoExistenteException.class)
				.hasMessage(MensagemValidacao.getMensagemValidacao("validacao.excecao.usuario.encontrado"));
	}

	@Test
	public void consultaUsuarioSucesso() throws RecursoNaoEncontradoException {
		Usuario usuario = getMockUsuario();
		when(repository.findById(usuario.getCpf())).thenReturn(Optional.of(usuario));
		Usuario usuarioPorId = service.consultaUsuarioPorId(usuario.getCpf(),true);

		assertNotNull(usuarioPorId);
		assertEquals(usuario.getCpf(), usuarioPorId.getCpf());
	}

	@Test
	public void consultaUsuarioException() throws RecursoNaoEncontradoException {
		Usuario usuario = getMockUsuario();
		when(repository.findById(usuario.getCpf())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.consultaUsuarioPorId(usuario.getCpf(), true)).isInstanceOf(RecursoNaoEncontradoException.class)
				.hasMessage(MensagemValidacao.getMensagemValidacao("validacao.excecao.usuario.nao.encontrado"));
	}

	@Test
	public void consultaUsuarioNull() throws RecursoNaoEncontradoException {
		Usuario usuario = getMockUsuario();
		when(repository.findById(usuario.getCpf())).thenReturn(Optional.empty());

		Usuario metodoConsulta = service.consultaUsuarioPorId(usuario.getCpf(), false);
		assertNull(metodoConsulta);
	}

	@Test
	public void consultaPaginadaUsuariosSucesso() throws RecursoNaoEncontradoException {

		List<Usuario> list = Arrays.asList(getMockUsuario(), getMockOutroUsuario());
		when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(list));

		Page<Usuario> pageable = service.consultaUsuariosPaginada(Pageable.ofSize(2));
		assertNotNull(pageable);
		assertEquals(pageable.getContent().size(), list.size());
	}

	@Test
	public void alteraUsuarioSucesso() throws RecursoNaoEncontradoException {

		AlteraUsuarioDTO alteraUsuarioDto = getAlteraUsuarioDto();
		Usuario usuario = getMockUsuario();
		when(repository.findById(usuario.getCpf())).thenReturn(Optional.of(usuario));
		when(repository.save(usuario)).thenReturn(usuario);

		Usuario alteracao = service.alteraUsuario(alteraUsuarioDto, usuario.getCpf());
		assertNotNull(alteracao);
		assertEquals(usuario.getCpf(),alteracao.getCpf());
	}

	@Test
	public void alteraUsuarioException() throws RecursoNaoEncontradoException {
		AlteraUsuarioDTO alteraUsuarioDto = getAlteraUsuarioDto();
		Usuario usuario = getMockUsuario();

		when(repository.findById(usuario.getCpf())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.alteraUsuario(alteraUsuarioDto, usuario.getCpf())).isInstanceOf(RecursoNaoEncontradoException.class)
				.hasMessage(MensagemValidacao.getMensagemValidacao("validacao.excecao.usuario.nao.encontrado"));
	}

		@Test
	public void deletaUsuarioSucesso() throws RecursoNaoEncontradoException {

		Usuario usuario = getMockUsuario();

		when(repository.findById(usuario.getCpf())).thenReturn(Optional.of(usuario));
		doNothing().when(repository).delete(usuario);

		service.deletaUsuario(usuario.getCpf());
		verify(repository, times(1)).delete(usuario);
	}

	@Test
	public void deletaUsuarioException() throws RecursoNaoEncontradoException {
		Usuario usuario = getMockUsuario();
		when(repository.findById(usuario.getCpf())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.deletaUsuario(usuario.getCpf())).isInstanceOf(RecursoNaoEncontradoException.class)
				.hasMessage(MensagemValidacao.getMensagemValidacao("validacao.excecao.usuario.nao.encontrado"));
	}


	@Test
	public void buscaAvancadaSucesso() throws RecursoNaoEncontradoException {

		BuscaAvancadaDTO buscaAvancadaDto = getMockBuscaAvancadaDto();
		Usuario usuario = getMockUsuario();
		when(mapper.map( buscaAvancadaDto, Usuario.class)).thenReturn(usuario);
		when(mapper.map(buscaAvancadaDto, Endereco.class)).thenReturn(usuario.getEndereco());

		Example<Usuario> example = Example.of(usuario);
		when(repository.findAll(example)).thenReturn(Collections.singletonList(usuario));
		List<Usuario> buscaAvancada = service.buscaAvancadaUsuario(buscaAvancadaDto);
		assertNotNull(buscaAvancada);
	}

	@Test
	public void buscaAvancadaException() throws RecursoNaoEncontradoException {
		BuscaAvancadaDTO buscaAvancadaDto = new BuscaAvancadaDTO();

		assertThatThrownBy(() -> service.buscaAvancadaUsuario(buscaAvancadaDto)).isInstanceOf(FiltroException.class)
				.hasMessage(MensagemValidacao.getMensagemValidacao("validacao.excecao.sem.filtro"));
	}
}
