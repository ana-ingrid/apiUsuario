package br.com.cadastro.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;


public class CadastraUsuarioDTO {

    @NotNull(message = "Nome Obrigatório")
    private String nome;

    @NotNull(message = "CPF obrigatório")
    @CPF
    private String cpf;

    @NotNull(message = "Sexo obrigatório")
    private String sexo;

    @NotNull(message = "Data obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nascimento;

    @NotNull(message = "Endereço obrigatório")
    private EnderecoDTO endereco;

    public String getNome() {
        return nome;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
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

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }


}
