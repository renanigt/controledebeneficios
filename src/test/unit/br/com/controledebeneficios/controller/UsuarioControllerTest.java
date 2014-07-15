package br.com.controledebeneficios.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
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
	
	private Usuario usuarioRenan;
	private Usuario usuarioIago;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		validator = new MockValidator();

		controller = new UsuarioController(result, validator, service);
		
		usuarioRenan = new UsuarioBuilder().comId(1).comLogin("renanigt").comSenha("teste123").build();
		usuarioIago= new UsuarioBuilder().comId(2).comLogin("iagota").comSenha("321teste").build();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void deveriaAbrirInicialComTodosUsuarios() {
		when(service.lista()).thenReturn(Arrays.asList(usuarioRenan, usuarioIago));
		
		controller.index();
		
		assertTrue("Deve conter lista de usuários", result.included().containsKey("usuarios"));
		Assert.assertThat((List<Usuario>) result.included("usuarios"), containsInAnyOrder(usuarioRenan, usuarioIago));
	}

	@Test
	public void deveriaAdicionarUsuario() {
		Usuario usuario = new UsuarioBuilder().comLogin("fulano").comSenha("fulano123").build();
		
		controller.adiciona(usuario);
		
		verify(service).salvar(usuario);
		
		Assert.assertThat(result.included("sucesso").toString(), is("Usuário adicionado com sucesso."));
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveriaSalvarUsuarioSemDados() {
		Usuario usuario = new UsuarioBuilder().comLogin("").comSenha("").build();
		
		controller.adiciona(usuario);
	}
	
}
