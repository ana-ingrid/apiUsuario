package br.com.cadastro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_usuario")
public class Usuario {

	@Column
	private String nome;
	@Column
	@Id
	private String cpf;
	@Column
	private String sexo;
//	@Column
//	private Date nascimento;
//	private Endereco endereco;
	
	public String getNome() {
		return nome;
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
//	public Date getNascimento() {
//		return nascimento;
//	}
//	public void setNascimento(Date nascimento) {
//		this.nascimento = nascimento;
//	}
	
	
}
