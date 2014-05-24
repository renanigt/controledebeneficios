package br.com.controledebeneficios.model;

public class Beneficio {

	private Beneficiadora beneficiadora;
	private Integer pontos;
	
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
