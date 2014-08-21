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
		
		verify(service).salva(beneficiadora);
		
		assertThat(result.included("sucesso").toString(), is("Beneficiadora adicionada com sucesso."));
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveriaSalvarBeneficiadoraSemDados() {
		Beneficiadora beneficiadora = new BeneficiadoraBuilder().comNome("").comTipoBeneficio(null).build();
		
		controller.adiciona(beneficiadora);
	}
	
	@Test
	public void deveriaRemoverBeneficiadora() {
		when(service.pesquisarPorId(unimed.getId())).thenReturn(unimed);
		
		controller.delete(unimed.getId());
		
		verify(service).deleta(unimed);
		
		assertThat(result.included("sucesso").toString(), is("Beneficiadora removida com sucesso."));
	}

	@Test
	public void deveriaExibirMensagemAoTentarRemoverBeneficiadoraInexistente() {
		when(service.pesquisarPorId(anyInt())).thenReturn(null);
		
		controller.delete(anyInt());
		
		verify(service, never()).deleta(any(Beneficiadora.class));
		
		assertThat(result.included("erro").toString(), is("Beneficiadora inexistente."));
	}
	
	@Test
	public void deveriaAlterarBeneficiadora() {
		Beneficiadora beneficiadora = new BeneficiadoraBuilder().comNome("Uniodonto").
				comTipoBeneficio(TipoBeneficio.PLANO_ODONTOLOGICO).build();
		
		controller.atualiza(beneficiadora);
		
		verify(service).atualiza(beneficiadora);
		
		assertThat(result.included("sucesso").toString(), is("Beneficiadora atualizada com sucesso."));
	}

	@Test(expected=ValidationException.class)
	public void naoDeveriaAlterarBeneficiadoraSemDados() {
		Beneficiadora beneficiadora = new BeneficiadoraBuilder().comNome("").
				comTipoBeneficio(null).build();
		
		controller.atualiza(beneficiadora);
	}
	
}
