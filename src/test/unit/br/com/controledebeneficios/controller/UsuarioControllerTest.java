package br.com.controledebeneficios.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;
import br.com.controledebeneficios.builder.UsuarioBuilder;
import br.com.controledebeneficios.model.Usuario;
import br.com.controledebeneficios.service.UsuarioService;

public class UsuarioControllerTest {

	private MockResult result;
	private MockValidator validator;
	@Mock
	private UsuarioService service;
	
	private UsuarioController controller;
	
	private Usuario usuarioFulano;
	private Usuario usuarioCicrano;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		validator = new MockValidator();

		controller = new UsuarioController(result, validator, service);
		
		usuarioFulano = new UsuarioBuilder().comId(1).comLogin("fulano").comSenha("teste123").build();
		usuarioCicrano = new UsuarioBuilder().comId(2).comLogin("cicrano").comSenha("321teste").build();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void deveriaAbrirPaginaInicialComTodosUsuarios() {
		when(service.lista()).thenReturn(Arrays.asList(usuarioFulano, usuarioCicrano));
		
		controller.index();
		
		assertTrue("Deve conter lista de usuários", result.included().containsKey("usuarios"));
		assertThat((List<Usuario>) result.included("usuarios"), containsInAnyOrder(usuarioFulano, usuarioCicrano));
	}

	@Test
	public void deveriaAdicionarUsuario() {
		Usuario usuario = new UsuarioBuilder().comLogin("fulano").comSenha("fulano123").build();
		
		controller.adiciona(usuario);
		
		verify(service).salva(usuario);
		
		assertThat(result.included("sucesso").toString(), is("Usuário adicionado com sucesso."));
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveriaSalvarUsuarioSemDados() {
		Usuario usuario = new UsuarioBuilder().comLogin("").comSenha("").build();
		
		controller.adiciona(usuario);
	}

	@Test(expected = ValidationException.class)
	public void naoDeveriaSalvarUsuarioComLoginExistente() {
		Usuario usuario = new UsuarioBuilder().comLogin("fulano").comSenha("senha123").build();

		when(service.pesquisaPorLogin(usuario.getLogin())).thenReturn(usuarioFulano);
		
		controller.adiciona(usuario);
	}
	
	@Test
	public void deveriaRemoverUsuario() {
		when(service.pesquisaPorId(usuarioFulano.getId())).thenReturn(usuarioFulano);
		
		controller.delete(usuarioFulano.getId());
		
		verify(service).delete(usuarioFulano);
		
		assertThat(result.included("sucesso").toString(), is("Usuário removido com sucesso."));
	}
	
	@Test
	public void deveriaExibirMensagemAoTentarRemoverUsuarioInexistente() {
		when(service.pesquisaPorId(anyInt())).thenReturn(null);
		
		controller.delete(anyInt());
		
		verify(service, never()).delete(any(Usuario.class));
		
		assertThat(result.included("sucesso").toString(), is("Usuário inexistente."));
	}
	
}
