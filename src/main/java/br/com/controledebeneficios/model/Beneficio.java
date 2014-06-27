package br.com.controledebeneficios.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Beneficio {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Beneficiadora beneficiadora;
	private Integer pontos;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Beneficiadora getBeneficiadora() {
		return beneficiadora;
	}

	public void setBeneficiadora(Beneficiadora beneficiadora) {
		this.beneficiadora = beneficiadora;
	}

	public Integer getPontos() {
		return pontos;
	}

	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}
	
}
