package br.com.controledebeneficios.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Empresa {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String nome;
	private Integer cnpj;
	@OneToMany
	private List<Funcionario> funcionarios;
	@OneToOne
	private Usuario usuario;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
