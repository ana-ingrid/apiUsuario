package br.com.cadastro.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="tb_usuarios")
public class Usuario implements Serializable {

	@Column
	private String nome;
	
	@Column
	@Id
	private String cpf;
	
	@Column
	private String sexo;
	
	@Column(name="nascimento")
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
