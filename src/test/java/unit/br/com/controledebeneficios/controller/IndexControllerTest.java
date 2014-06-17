package br.com.controledebeneficios.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.controledebeneficios.controller.IndexController;

public class IndexControllerTest {

	private MockResult result;
	private IndexController controller;
	
	@Before
	public void setUp() {
		result = new MockResult();
		controller = new IndexController(result);
	}
	
	@Test
	public void shouldHaveSuccessMessage() {
		controller.index();
		
		assertTrue(result.included().containsKey("success"));
		assertThat(result.included("success").toString(), is("VRaptor is Working!"));
	}
	
}
