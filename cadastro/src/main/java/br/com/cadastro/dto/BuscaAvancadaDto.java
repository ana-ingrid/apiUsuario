package br.com.cadastro.dto;

import java.util.Date;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;


public class BuscaAvancadaDto {

	@CPF
	private String cpf;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date Nascimento;
	
	private String sexo;
	
	private String cidade;
	
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
