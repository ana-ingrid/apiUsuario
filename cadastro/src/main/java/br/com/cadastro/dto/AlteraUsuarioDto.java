package br.com.cadastro.dto;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;



public class AlteraUsuarioDto {

	@NotNull(message="Nome obrigatório")
	private String nome;
	
	@NotNull(message="Sexo obrigatório")
	@Size(min = 1, max = 20, message = "Inválido, tamanho deve ser de 1 a 20 caracters")
	private String sexo;
	
	@NotNull(message="Data obrigatória")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate nascimento;
	
	private EnderecoDto endereco;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoDto endereco) {
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
