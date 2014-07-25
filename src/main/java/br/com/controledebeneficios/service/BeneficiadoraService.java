package br.com.controledebeneficios.service;

import java.util.List;

import br.com.controledebeneficios.model.Beneficiadora;

public interface BeneficiadoraService {

	public Beneficiadora pesquisarPorId(Integer id);

	public void salva(Beneficiadora beneficiadora);

	public List<Beneficiadora> lista();

	public void atualiza(Beneficiadora beneficiadora);

	public void deleta(Beneficiadora beneficiadora);

	public List<Beneficiadora> pesquisarBeneficiadora(Beneficiadora beneficiadora);
	
}
