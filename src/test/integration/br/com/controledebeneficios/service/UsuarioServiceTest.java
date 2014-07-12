package br.com.controledebeneficios.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.controledebeneficios.builder.UsuarioBuilder;
import br.com.controledebeneficios.model.Usuario;

public class UsuarioServiceTest extends DataBaseTestCase {

	private UsuarioService service;
	
	@Before
	public void setUp() {
		service = new UsuarioService(manager);
		
		dadosIniciais();
	}
	
	@Test
	public void deveriaListarTodosOsUsuarios() {
		assertThat(service.lista().size(), is(3));
	}

	@Test
	public void deveriaSalvarUsuario() {
		Usuario usuario = createUsuario();
		service.salvar(usuario);
		
		assertThat(service.lista().size(), is(4));
	}

	private Usuario createUsuario() {
		Usuario usuario = new Usuario();
		usuario.setLogin("renanigt");
		usuario.setSenha("teste123");
		
		return usuario;
	}
	
	private void dadosIniciais() {
		Usuario usuario1 = new UsuarioBuilder().comLogin("fulano").comSenha("123teste").build();
		Usuario usuario2 = new UsuarioBuilder().comLogin("cicrano").comSenha("cicrano123").build();
		Usuario usuario3 = new UsuarioBuilder().comLogin("beltrano").comSenha("beltrano123").build();
		
		List<Usuario> usuarios = Arrays.asList(usuario1, usuario2, usuario3);
		
		for(Usuario usuario: usuarios) {
			manager.persist(usuario);
		}
	}
	
}
