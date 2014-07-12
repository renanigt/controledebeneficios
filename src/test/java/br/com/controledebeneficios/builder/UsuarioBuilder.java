package br.com.controledebeneficios.builder;

import br.com.controledebeneficios.model.Usuario;

public class UsuarioBuilder {

	private final Usuario usuario = new Usuario();
	
	public UsuarioBuilder comId(Integer id) {
		usuario.setId(id);
		return this;
	}

	public UsuarioBuilder comLogin(String login) {
		usuario.setLogin(login);
		return this;
	}

	public UsuarioBuilder comSenha(String senha) {
		usuario.setSenha(senha);
		return this;
	}
	
	public Usuario build() {
		return usuario;
	}
	
}
