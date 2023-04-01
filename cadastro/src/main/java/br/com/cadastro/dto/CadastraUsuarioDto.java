package br.com.cadastro.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;


public class CadastraUsuarioDto {

	@NotNull(message = "Nome Obrigatório")
	private String nome;

	@NotNull(message = "CPF obrigatório")
	private String cpf;

	@NotNull(message = "Sexo obrigatório")
	private String sexo;

	@NotNull(message = "Data obrigatória")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date nascimento;
		
	private EnderecoDto endereco;

	public String getNome() {
		return nome;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {
		this.endereco = endereco;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
		
	
}
