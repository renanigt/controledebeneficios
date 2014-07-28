package br.com.controledebeneficios.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;
import br.com.controledebeneficios.builder.BeneficiadoraBuilder;
import br.com.controledebeneficios.model.Beneficiadora;
import br.com.controledebeneficios.model.TipoBeneficio;
import br.com.controledebeneficios.service.BeneficiadoraService;

public class BeneficiadoraControllerTest {

	private MockResult result;
	private MockValidator validator;
	@Mock
	private BeneficiadoraService service;
	
	private BeneficiadoraController controller;
	
	private Beneficiadora aleloRefeicao;
	private Beneficiadora unimed;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		validator = new MockValidator();
		controller = new BeneficiadoraController(result, validator, service); 
		
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
	
	@Test
	public void deveriaAdicionarUmaBeneficiadora() {
		Beneficiadora beneficiadora = new BeneficiadoraBuilder().comNome("Uniodonto").
				comTipoBeneficio(TipoBeneficio.PLANO_ODONTOLOGICO).build();
		
		controller.adiciona(beneficiadora);
		
		Mockito.verify(service).salva(beneficiadora);
		
		assertThat(result.included("sucesso").toString(), is("Beneficiadora adicionada com sucesso."));
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveriaSalvarBeneficiadoraSemDados() {
		Beneficiadora beneficiadora = new BeneficiadoraBuilder().comNome("").comTipoBeneficio(null).build();
		
		controller.adiciona(beneficiadora);
	}
	
}
