package br.com.cadastro.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.*;

import br.com.cadastro.config.MensagemValidacao;
import br.com.cadastro.exception.FiltroException;
import classe.ObjetoJson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.cadastro.dto.AlteraUsuarioDto;
import br.com.cadastro.dto.BuscaAvancadaDto;
import br.com.cadastro.dto.CadastraUsuarioDto;
import br.com.cadastro.exception.RecursoExistenteException;
import br.com.cadastro.exception.RecursoNaoEncontradoException;
import br.com.cadastro.model.Endereco;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.repository.UsuarioRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UsuarioServiceTest {

	protected static final String USUARIO_JSON = "usuario.json";

	protected static final String CADASTRA_USUARIO_DTO_JSON = "cadastraUsuarioDto.json";

	protected static final String ALTERA_USUARIO_DTO_JSON = "alteraUsuarioDto.json";

	protected static final String OUTRO_USUARIO_JSON = "outroUsuario.json";

	protected static final String BUSCA_AVANCADA_DTO_JSON = "buscaAvancadaDto.json";

	protected static final String USUARIO_PATH = "/mocks";

	@InjectMocks
	private UsuarioService service;

	@Mock
	private UsuarioRepository repository;

	@Mock
	private ModelMapper mapper;


	protected static Usuario getMockUsuario() {
		return ObjetoJson.getMockObject(USUARIO_PATH, USUARIO_JSON, Usuario.class);
	}

	protected static Usuario getMockOutroUsuario() {
		return ObjetoJson.getMockObject(USUARIO_PATH, OUTRO_USUARIO_JSON, Usuario.class);
	}

	protected static CadastraUsuarioDto getMockCadastraUsuarioDto() {
		return ObjetoJson.getMockObject(USUARIO_PATH, CADASTRA_USUARIO_DTO_JSON, CadastraUsuarioDto.class);
	}

	protected static AlteraUsuarioDto getAlteraUsuarioDto() {
		return ObjetoJson.getMockObject(USUARIO_PATH, ALTERA_USUARIO_DTO_JSON, AlteraUsuarioDto.class);
	}

	protected static BuscaAvancadaDto getMockBuscaAvancadaDto() {
		return ObjetoJson.getMockObject(USUARIO_PATH, BUSCA_AVANCADA_DTO_JSON, BuscaAvancadaDto.class);
	}

	@Test
	public void cadastraUsuarioSucesso() throws RecursoExistenteException {
		CadastraUsuarioDto usuarioDto = getMockCadastraUsuarioDto();
		Usuario usuario = getMockUsuario();

		when(repository.findById(usuarioDto.getCpf())).thenReturn(Optional.empty());
		when(mapper.map(any(), any())).thenReturn(usuario);
		when(repository.save(usuario)).thenReturn(usuario);

		Usuario metodoCadastra = service.cadastraUsuario(usuarioDto);
		assertEquals(usuario.getCpf(), metodoCadastra.getCpf());
	}
	@Test
	public void cadastraUsuarioException() {
		CadastraUsuarioDto usuarioDto = getMockCadastraUsuarioDto();
		Usuario usuario = getMockUsuario();
		when(repository.findById(usuarioDto.getCpf())).thenReturn(Optional.of(usuario));

		assertThatThrownBy(() -> {
			service.cadastraUsuario(usuarioDto);
		}).isInstanceOf(RecursoExistenteException.class)
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

		assertThatThrownBy(() -> {
			service.consultaUsuarioPorId(usuario.getCpf(), true);
		}).isInstanceOf(RecursoNaoEncontradoException.class)
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
		when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<Usuario>(list));

		Page<Usuario> pageable = service.consultaUsuariosPaginada(Pageable.ofSize(2));
		assertNotNull(pageable);
		assertEquals(pageable.getContent().size(), list.size());
	}

	@Test
	public void alteraUsuarioSucesso() throws RecursoNaoEncontradoException, RecursoExistenteException {

		AlteraUsuarioDto alteraUsuarioDto = getAlteraUsuarioDto();
		Usuario usuario = getMockUsuario();
		when(repository.findById(usuario.getCpf())).thenReturn(Optional.of(usuario));
		when(repository.save(usuario)).thenReturn(usuario);

		Usuario alteracao = service.alteraUsuario(alteraUsuarioDto, usuario.getCpf());
		assertNotNull(alteracao);
		assertEquals(usuario.getCpf(),alteracao.getCpf());
	}

	@Test
	public void alteraUsuarioException() throws RecursoNaoEncontradoException, RecursoExistenteException {
		AlteraUsuarioDto alteraUsuarioDto = getAlteraUsuarioDto();
		Usuario usuario = getMockUsuario();

		when(repository.findById(usuario.getCpf())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> {
			service.alteraUsuario(alteraUsuarioDto, usuario.getCpf());
		}).isInstanceOf(RecursoNaoEncontradoException.class)
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

		assertThatThrownBy(() -> {
			service.deletaUsuario(usuario.getCpf());
					}).isInstanceOf(RecursoNaoEncontradoException.class)
				.hasMessage(MensagemValidacao.getMensagemValidacao("validacao.excecao.usuario.nao.encontrado"));
	}


	@Test
	public void buscaAvancadaSucesso() throws RecursoNaoEncontradoException {

		BuscaAvancadaDto buscaAvancadaDto = getMockBuscaAvancadaDto();
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
		BuscaAvancadaDto buscaAvancadaDto = new BuscaAvancadaDto();

		assertThatThrownBy(() -> {
			service.buscaAvancadaUsuario(buscaAvancadaDto);
		}).isInstanceOf(FiltroException.class)
				.hasMessage(MensagemValidacao.getMensagemValidacao("validacao.excecao.sem.filtro"));
	}
}
