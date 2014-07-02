package br.com.controledebeneficios.service;

import javax.persistence.EntityManager;

import br.com.controledebeneficios.model.Usuario;

public class UsuarioService {

	private EntityManager manager;
	
	public UsuarioService(EntityManager manager) {
		this.manager = manager;
	}
	
	public void salvar(Usuario usuario) {
		manager.persist(usuario);
	}

}
