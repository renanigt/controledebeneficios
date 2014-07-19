package br.com.controledebeneficios.model;

public enum TipoBeneficio {

	VALE_ALIMENTACAO("Vale Alimentação"),
	VALE_REFEICAO("Vale Refeição"),
	PLANO_ODONTOLOGICO("Plano Odontológico"),
	PLANO_SAUDE("Plano de Saúde");
	
	private String descricao;
	
	private TipoBeneficio(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
