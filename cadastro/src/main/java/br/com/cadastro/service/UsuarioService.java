package br.com.cadastro.service;

import br.com.cadastro.config.MensagemValidacao;
import br.com.cadastro.dto.AlteraUsuarioDto;
import br.com.cadastro.dto.BuscaAvancadaDto;
import br.com.cadastro.dto.CadastraUsuarioDto;
import br.com.cadastro.exception.FiltroException;
import br.com.cadastro.exception.RecursoExistenteException;
import br.com.cadastro.exception.RecursoNaoEncontradoException;
import br.com.cadastro.model.Endereco;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static br.com.cadastro.config.MensagemValidacao.getMensagemValidacao;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Usuario consultaUsuarioPorId(String cpf, boolean withThrow) throws RecursoNaoEncontradoException {
        return repository.findById(cpf)
                .orElseGet(() -> {
                    if (withThrow)
                        throw new RecursoNaoEncontradoException(getMensagemValidacao("validacao.excecao.usuario.nao.encontrado"));
                    return null;
                });
    }

    public Usuario cadastraUsuario(CadastraUsuarioDto cadastraUsuarioDto) throws RecursoExistenteException {
        if (Objects.nonNull(consultaUsuarioPorId(cadastraUsuarioDto.getCpf(), false))) {
            throw new RecursoExistenteException(MensagemValidacao.getMensagemValidacao("validacao.excecao.usuario.encontrado"));
        }

        Usuario usuario = modelMapper.map(cadastraUsuarioDto, Usuario.class);
        return repository.save(usuario);
    }

    public Page<Usuario> consultaUsuariosPaginada(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Usuario alteraUsuario(AlteraUsuarioDto alteraUsuarioDto, String cpf) throws RecursoNaoEncontradoException {
        Usuario user = consultaUsuarioPorId(cpf, true);
        modelMapper.map(alteraUsuarioDto, user);
        return repository.save(user);
    }

    public void deletaUsuario(String cpf) throws RecursoNaoEncontradoException {
        Usuario user = consultaUsuarioPorId(cpf, true);
        repository.delete(user);
    }

    public List<Usuario> buscaAvancadaUsuario(BuscaAvancadaDto buscaAvancadaDto) throws RecursoNaoEncontradoException {

        if (Objects.isNull(buscaAvancadaDto.getCpf()) && Objects.isNull(buscaAvancadaDto.getNascimento()) &&
                Objects.isNull(buscaAvancadaDto.getSexo()) && Objects.isNull(buscaAvancadaDto.getNome())
                && Objects.isNull(buscaAvancadaDto.getCidade()) && Objects.isNull(buscaAvancadaDto.getCep())
                && Objects.isNull(buscaAvancadaDto.getUf())) {
            throw new FiltroException(getMensagemValidacao("validacao.excecao.sem.filtro"));
        }

        Usuario usuario = modelMapper.map(buscaAvancadaDto, Usuario.class);
        Endereco endereco = modelMapper.map(buscaAvancadaDto, Endereco.class);
        usuario.setEndereco(endereco);
        Example<Usuario> example = Example.of(usuario);
        return repository.findAll(example);
    }
}