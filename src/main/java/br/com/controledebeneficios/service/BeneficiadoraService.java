package br.com.controledebeneficios.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.controledebeneficios.model.Beneficiadora;

public class BeneficiadoraService {

	private EntityManager manager;
	
	/**
	 * @deprecated CDI eyes only
	 */
	public BeneficiadoraService() {
	}

	@Inject
	public BeneficiadoraService(EntityManager manager) {
		this.manager = manager;
	}

	public Beneficiadora pesquisarPorId(Integer id) {
		return manager.find(Beneficiadora.class, id);
	}

	public void salva(Beneficiadora beneficiadora) {
		manager.persist(beneficiadora);
	}

	@SuppressWarnings("unchecked")
	public List<Beneficiadora> lista() {
		String jpql = "from Beneficiadora order by nome";
		
		Query query = manager.createQuery(jpql);

		return query.getResultList();
	}

	public void atualiza(Beneficiadora beneficiadora) {
		manager.merge(beneficiadora);
	}

	public void deleta(Beneficiadora beneficiadora) {
		manager.remove(beneficiadora);
	}
	
}
