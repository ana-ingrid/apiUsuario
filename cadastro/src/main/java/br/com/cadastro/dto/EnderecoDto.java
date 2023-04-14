package br.com.cadastro.dto;

import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

public class EnderecoDto {
	
	@NotNull
	@Size(max = 50, message = "Inválido, máximo de 100 caracteres")
	private String logradouro;
	
	@Size(min = 1 , max = 10, message = "Número inválido")
	private Integer numero;
	
	@NotNull
	@Size(max = 50, message = "Inválido, máximo de 50 caracteres")
	private String cidade;
	
	@NotNull
	@Size(min = 1, max = 20, message = "Inválido, tamanho deve ser 2 caracteres")
	private String uf;


	public EnderecoDto() {
		
	}
	

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}	


	
}
