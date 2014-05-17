package br.com.controledebeneficios.model;

import java.util.List;

public class Empresa {

	private String nome;
	private Integer cnpj;
	private List<Funcionario> funcionarios;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(Integer cnpj) {
		this.cnpj = cnpj;
	}
	
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
}
