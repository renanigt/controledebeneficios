package br.com.controledebeneficios.service;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class BeneficiadoraService {

	private EntityManager manager;
	
	/**
	 * @deprecated CDI eyes only
	 */
	public BeneficiadoraService() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	public BeneficiadoraService(EntityManager manager) {
		this.manager = manager;
	}
	
}
