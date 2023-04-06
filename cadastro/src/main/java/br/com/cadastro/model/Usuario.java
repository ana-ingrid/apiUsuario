package br.com.cadastro.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.cadastro.dto.EnderecoDto;

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
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date nascimento;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_endereco", referencedColumnName="id")
	private Endereco endereco;

	public Usuario() {
		
	}
	
	// construtor para cadastro
	public Usuario( String nome2, String cpf2, String sexo2, Date nascimento) {
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

	public Date getNascimento() {
		return nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	
	
}
