package br.com.controledebeneficios.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.controledebeneficios.model.Usuario;
import br.com.controledebeneficios.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {

	private EntityManager manager;
	
	/**
	 * @deprecated CDI eyes only
	 */
	public UsuarioServiceImpl() {
	}
	
	@Inject
	public UsuarioServiceImpl(EntityManager manager) {
		this.manager = manager;
	}

	public Usuario pesquisaPorId(Integer id) {
		return manager.find(Usuario.class, id);
	}

	public void salva(Usuario usuario) {
		manager.persist(usuario);
	}
	
	public void atualiza(Usuario usuario) {
		manager.merge(usuario);
	}
	
	public void delete(Usuario usuario) {
		manager.remove(usuario);
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> lista() {
		String jpql = "from Usuario order by login";
		
		Query query = manager.createQuery(jpql);

		return query.getResultList();
	}

	public Usuario pesquisaPorLogin(String login) {
		String jpql = "from Usuario where login = :login";
		
		Query query = manager.createQuery(jpql);
		
		query.setParameter("login", login);
		
		try {
			return (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
