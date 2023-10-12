package br.com.cadastro.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;



public class AlteraUsuarioDTO {

	@NotNull(message="Nome obrigatório")
	private String nome;
	
	@NotNull(message="Sexo obrigatório")
	@Size(min = 1, max = 20, message = "Inválido, tamanho deve ser de 1 a 20 caracters")
	private String sexo;
	
	@NotNull(message="Data obrigatória")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate nascimento;
	
	private EnderecoDTO endereco;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public LocalDate getNascimento() {
		return nascimento;
	}
	public void setNascimento(LocalDate localDate) {
		this.nascimento = localDate;
	}
	

}
