package br.com.controledebeneficios.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.controledebeneficios.builder.BeneficiadoraBuilder;
import br.com.controledebeneficios.model.Beneficiadora;
import br.com.controledebeneficios.model.TipoBeneficio;
import br.com.controledebeneficios.service.BeneficiadoraService;

public class BeneficiadoraControllerTest {

	private MockResult result;
	@Mock
	private BeneficiadoraService service;
	
	private BeneficiadoraController controller;
	
	private Beneficiadora aleloRefeicao;
	private Beneficiadora unimed;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		controller = new BeneficiadoraController(result, service); 
		
		aleloRefeicao = new BeneficiadoraBuilder().comNome("Alelo Refeição").comTipoBeneficio(TipoBeneficio.VALE_REFEICAO).build();
		unimed = new BeneficiadoraBuilder().comNome("Unimed").comTipoBeneficio(TipoBeneficio.PLANO_SAUDE).build();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void deveriaAbrirPaginaInicialComListaDeBeneficiadoras() {
		when(service.lista()).thenReturn(Arrays.asList(aleloRefeicao, unimed));
		
		controller.index();
		
		assertTrue("Deve conter lista de beneficiadoras", result.included().containsKey("beneficiadoras"));
		assertThat((List<Beneficiadora>) result.included("beneficiadoras"), containsInAnyOrder(unimed, aleloRefeicao));
	}
	
}
