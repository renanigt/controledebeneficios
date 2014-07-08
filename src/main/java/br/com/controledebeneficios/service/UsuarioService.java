package br.com.controledebeneficios.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.controledebeneficios.model.Usuario;

public class UsuarioService {

	private EntityManager manager;
	
	@Inject
	public UsuarioService(EntityManager manager) {
		this.manager = manager;
	}

	public void salvar(Usuario usuario) {
		manager.persist(usuario);
	}
	
	public List<Usuario> lista() {
		String hql = "from Usuario order by login";
		
		Query query = manager.createQuery(hql);

		return query.getResultList();
	}
	
}
