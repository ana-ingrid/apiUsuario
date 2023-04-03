package br.com.cadastro.service;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.hibernate.query.criteria.internal.expression.SearchedCaseExpression.WhenClause;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import br.com.cadastro.dto.CadastraUsuarioDto;
import br.com.cadastro.exception.UsuarioExistenteException;
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
		// cenário
		CadastraUsuarioDto dto = new CadastraUsuarioDto();
		dto.setNome("ingrid");
		dto.setCpf("111111");
		dto.setNascimento(new Date());
		dto.setSexo("feminino");
		Usuario user = new Usuario();
		user.setNome(dto.getNome());
		user.setCpf(dto.getCpf());
		user.setSexo(dto.getSexo());
		Mockito.when(repository.findById(dto.getCpf())).thenReturn(Optional.empty());
		Mockito.when(mapper.map(dto, Usuario.class)).thenReturn(user);
		Mockito.when(repository.save(user)).thenReturn(user);
//		dto.setEndereco();		
		//executa
		 Usuario user1 = service.cadastraUsuario(dto);
		 //valida
		 assertNotNull(user1);
	}
	
	
}
