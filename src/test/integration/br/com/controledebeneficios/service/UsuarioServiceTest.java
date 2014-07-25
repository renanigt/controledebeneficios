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
import br.com.controledebeneficios.service.impl.UsuarioServiceImpl;

public class UsuarioServiceTest extends DataBaseTestCase {

	private UsuarioService service;
	
	private Usuario usuarioFulano;
	private Usuario usuarioCicrano;
	private Usuario usuarioBeltrano;
	
	private Integer idValido;
	private Integer idInvalido = -1;
	
	@Before
	public void setUp() {
		service = new UsuarioServiceImpl(manager);
		
		dadosIniciais();
	}
	
	@Test
	public void deveriaPesquisarPorId() {
		Usuario usuarioInvalido = service.pesquisaPorId(idInvalido);
		Usuario usuario = service.pesquisaPorId(idValido);
		
		assertThat(usuarioInvalido, is(Matchers.nullValue()));
		assertThat(usuario, is(Matchers.notNullValue()));
		verificaUsuario(usuario, usuarioFulano);
	}
	
	@Test
	public void deveriaPesquisarPorLogin() {
		Usuario usuario = service.pesquisaPorLogin(usuarioFulano.getLogin());
		
		assertThat(usuario, notNullValue());
		verificaUsuario(usuario, usuarioFulano);
	}
	
	@Test
	public void deveriaSalvarUsuario() {
		Usuario usuario = new UsuarioBuilder().comLogin("renanigt").comSenha("teste123").build();
		service.salva(usuario);
		
		Usuario usuarioRetornado = service.pesquisaPorId(usuario.getId());
		
		assertThat(service.lista().size(), is(4));
		assertThat(usuarioRetornado, notNullValue());
		verificaUsuario(usuarioRetornado, usuario);
	}

	@Test
	public void deveriaAtualizarUsuario() {
		Usuario usuario = new UsuarioBuilder().comId(idValido).comLogin("Fulano Atualizado").comSenha("123456").build();
		
		service.atualiza(usuario);
		
		Usuario usuarioAtualizado = service.pesquisaPorId(idValido);
		
		assertThat(service.lista().size(), is(3));
		assertThat(usuarioAtualizado, is(notNullValue()));
		verificaUsuario(usuarioAtualizado, usuario);
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
	
	private void verificaUsuario(Usuario actual, Usuario expected) {
		assertThat(actual.getLogin(), is(expected.getLogin()));
		assertThat(actual.getSenha(), is(expected.getSenha()));
	}
	
	private void dadosIniciais() {
		manager.createQuery("delete from Usuario").executeUpdate();
		
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
