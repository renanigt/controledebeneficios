package br.com.controledebeneficios.builder;

import br.com.controledebeneficios.model.Beneficiadora;
import br.com.controledebeneficios.model.TipoBeneficio;

public class BeneficiadoraBuilder {

	private final Beneficiadora beneficiadora = new Beneficiadora();
	
	public BeneficiadoraBuilder comId(Integer id) {
		beneficiadora.setId(id);
		return this;
	}
	
	public BeneficiadoraBuilder comNome(String nome) {
		beneficiadora.setNome(nome);
		return this;
	}
	
	public BeneficiadoraBuilder comTipoBeneficio(TipoBeneficio tipoBeneficio) {
		beneficiadora.setTipoBeneficio(tipoBeneficio);
		return this;
	}
	
	public Beneficiadora build() {
		return beneficiadora;
	}
	
}
