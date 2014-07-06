package br.com.controledebeneficios.service;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class UsuarioService {

	private EntityManager manager;
	
	@Inject
	public UsuarioService(EntityManager manager) {
		this.manager = manager;
	}

}
