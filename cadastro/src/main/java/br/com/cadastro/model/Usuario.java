package br.com.cadastro.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@Column(name="dt_nascimento")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate nascimento;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_endereco", referencedColumnName="id")
	private Endereco endereco;

	public Usuario() {
		
	}
	
	// construtor para cadastro
	public Usuario( String nome2, String cpf2, String sexo2, LocalDate nascimento) {
		this.nome = nome2;
		this.cpf = cpf2;
		this.sexo = sexo2;
		this.nascimento = nascimento;
	}
	

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

	public LocalDate getNascimento() {
		return nascimento;
	}
	public void setNascimento(LocalDate date) {
		this.nascimento = date;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	
	
}
