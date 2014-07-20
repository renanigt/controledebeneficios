package br.com.controledebeneficios.service;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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

	public List<Beneficiadora> lista() {
		String jpql = "from Beneficiadora order by nome";
		
		return manager.createQuery(jpql, Beneficiadora.class).getResultList();
	}

	public void atualiza(Beneficiadora beneficiadora) {
		manager.merge(beneficiadora);
	}

	public void deleta(Beneficiadora beneficiadora) {
		manager.remove(beneficiadora);
	}

	public List<Beneficiadora> pesquisarBeneficiadora(Beneficiadora beneficiadora) {
		boolean contemNome = !isNullOrEmpty(beneficiadora.getNome());
		boolean contemTipoBeneficio = beneficiadora.getTipoBeneficio() != null;
		
		String jpql = "from Beneficiadora where 1=1";
		
		jpql += contemNome ? " and nome like :nome" : "";
		jpql += contemTipoBeneficio ? " and tipoBeneficio = :tipoBeneficio" : "";
		
		TypedQuery<Beneficiadora> query = manager.createQuery(jpql, Beneficiadora.class);
		
		if(contemNome) {
			query.setParameter("nome", "%" + beneficiadora.getNome() + "%");
		}
		if(contemTipoBeneficio) {
			query.setParameter("tipoBeneficio", beneficiadora.getTipoBeneficio());
		}
		
		return query.getResultList();
	}
	
}
