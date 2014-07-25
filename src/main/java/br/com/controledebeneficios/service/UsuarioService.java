package br.com.controledebeneficios.service;

import java.util.List;

import br.com.controledebeneficios.model.Usuario;

public interface UsuarioService {

	public Usuario pesquisaPorId(Integer id);

	public void salva(Usuario usuario);
	
	public void atualiza(Usuario usuario);
	
	public void delete(Usuario usuario);
	
	public List<Usuario> lista();

	public Usuario pesquisaPorLogin(String login);
	
}
