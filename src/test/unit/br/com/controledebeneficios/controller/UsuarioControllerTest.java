package br.com.controledebeneficios.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.controledebeneficios.builder.UsuarioBuilder;
import br.com.controledebeneficios.model.Usuario;
import br.com.controledebeneficios.service.UsuarioService;

public class UsuarioControllerTest {

	private MockResult result;
	@Mock
	private UsuarioService service;
	
	private UsuarioController controller;
	
	private Usuario usuarioRenan;
	private Usuario usuarioIago;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();

		controller = new UsuarioController(result, service);
		
		usuarioRenan = new UsuarioBuilder().comId(1).comLogin("renanigt").comSenha("teste123").build();
		usuarioIago= new UsuarioBuilder().comId(2).comLogin("iagota").comSenha("321teste").build();
	}
	
	@Test
	public void deveriaAbrirInicialComTodosUsuarios() {
		Mockito.when(service.lista()).thenReturn(Arrays.asList(usuarioRenan, usuarioIago));
		
		controller.index();
		
		assertTrue("Deve conter lista de usuários", result.included().containsKey("usuarios"));
		assertEquals(Arrays.asList(usuarioRenan, usuarioIago), result.included("usuarios"));
	}
	
}
