package br.com.controledebeneficios.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import br.com.controledebeneficios.builder.UsuarioBuilder;
import br.com.controledebeneficios.model.Usuario;

public class UsuarioServiceTest extends DataBaseTestCase {

	private UsuarioService service;
	
	private Usuario usuarioFulano;
	private Usuario usuarioCicrano;
	private Usuario usuarioBeltrano;
	
	private Integer idValido;
	private Integer idInvalido = -1;
	
	@Before
	public void setUp() {
		service = new UsuarioService(manager);
		
		dadosIniciais();
	}
	
	@Test
	public void deveriaPesquisarPorId() {
		Usuario usuarioInvalido = service.pesquisaPorId(idInvalido);
		Usuario usuario = service.pesquisaPorId(idValido);
		
		assertThat(usuarioInvalido, is(Matchers.nullValue()));
		assertThat(usuario, is(Matchers.notNullValue()));
		assertThat(usuario.getLogin(), is("fulano"));
		assertThat(usuario.getSenha(), is("123teste"));
	}
	
	@Test
	public void deveriaSalvarUsuario() {
		Usuario usuario = new UsuarioBuilder().comLogin("renanigt").comSenha("teste123").build();
		service.salvar(usuario);
		
		Usuario usuarioRetornado = service.pesquisaPorId(usuario.getId());
		
		assertThat(service.lista().size(), is(4));
		assertThat(usuarioRetornado, is(notNullValue()));
		assertThat(usuarioRetornado.getLogin(), is("renanigt"));
		assertThat(usuarioRetornado.getSenha(), is("teste123"));
	}

	@Test
	public void deveriaAtualizarUsuario() {
		Usuario usuario = service.pesquisaPorId(idValido);
		usuario.setSenha("123456");
		
		service.atualiza(usuario);
		
		assertThat(usuario, is(notNullValue()));
		assertThat(usuario.getLogin(), is("fulano"));
		assertThat(usuario.getSenha(), is("123456"));
	}
	
	@Test
	public void deveriaRemoverUsuario() {
		service.delete(usuarioFulano);
		
		Usuario usuarioRetornado = service.pesquisaPorId(idValido);
		
		assertThat(service.lista().size(), is(2));
		assertThat(usuarioRetornado, is(nullValue()));
	}

	@Test
	public void deveriaListarTodosOsUsuarios() {
		assertThat(service.lista().size(), is(3));
	}
	
	private void dadosIniciais() {
		usuarioFulano = new UsuarioBuilder().comLogin("fulano").comSenha("123teste").build();
		usuarioCicrano = new UsuarioBuilder().comLogin("cicrano").comSenha("cicrano123").build();
		usuarioBeltrano = new UsuarioBuilder().comLogin("beltrano").comSenha("beltrano123").build();
		
		List<Usuario> usuarios = Arrays.asList(usuarioFulano, usuarioCicrano, usuarioBeltrano);
		
		for(Usuario usuario: usuarios) {
			manager.persist(usuario);
		}
		
		idValido = usuarioFulano.getId();
	}
	
}
