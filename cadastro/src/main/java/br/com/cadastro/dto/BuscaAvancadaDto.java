package br.com.cadastro.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


public class BuscaAvancadaDto {

	@Size(min=10, max=10, message="CPF inválido")
	private String cpf;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date Nascimento;
	
	@Size(min = 1, max = 20, message = "Inválido, tamanho deve ser de 1 a 20 caracteres")
	private String sexo;
	
	@Size( max = 50, message = "Inválido, máximo de até 50 caracteres")
	private String cidade;
	
	@Size(min = 1, max = 20, message = "Inválido, tamanho deve ser 2 caracteres")
	private String uf;
	
	
	public Date getNascimento() {
		return Nascimento;
	}
	
	public void setNascimento(Date nascimento) {
		Nascimento = nascimento;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
