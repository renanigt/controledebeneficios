package br.com.controledebeneficios.controller;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class IndexControllerTest {

	@Inject
	private Result result;
	private IndexController controller;
	
	@Before
	public void setUp() {
		result = new MockResult();
		controller = new IndexController(result);
	}
	
	@Test
	public void shouldHaveSuccessMessage() {
		controller.index();
		
		Assert.assertTrue(result.included().containsKey("success"));
	}
	
}
