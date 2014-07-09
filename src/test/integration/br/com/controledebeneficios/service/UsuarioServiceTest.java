package br.com.controledebeneficios.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.com.controledebeneficios.model.Usuario;

public class UsuarioServiceTest extends DataBaseTestCase {

	private UsuarioService service;
	
	@Before
	public void setUp() {
		service = new UsuarioService(manager);
	}
	
	@Test
	public void deveriaSalvarUsuario() {
		Usuario usuario = createUsuario();
		service.salvar(usuario);
		
		assertThat(service.lista().size(), is(1));
	}

	private Usuario createUsuario() {
		Usuario usuario = new Usuario();
		usuario.setLogin("renanigt");
		usuario.setSenha("teste123");
		
		return usuario;
	}
	
}
