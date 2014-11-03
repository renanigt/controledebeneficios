package br.com.controledebeneficios.service.impl;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controledebeneficios.model.Beneficiadora;
import br.com.controledebeneficios.service.BeneficiadoraService;

public class BeneficiadoraServiceImpl implements BeneficiadoraService {

	private EntityManager manager;
	
	/**
	 * @deprecated CDI eyes only
	 */
	public BeneficiadoraServiceImpl() {
	}

	@Inject
	public BeneficiadoraServiceImpl(EntityManager manager) {
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
		Session session = manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(Beneficiadora.class);
		if(beneficiadora.getNome() != null) {
			criteria.add(Restrictions.ilike("nome", beneficiadora.getNome(), MatchMode.ANYWHERE));
		}
		if(beneficiadora.getTipoBeneficio() != null) {
			criteria.add(Restrictions.eq("tipoBeneficio", beneficiadora.getTipoBeneficio()));
		}
		
		return criteria.list();
	}
	
}
