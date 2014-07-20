package br.com.controledebeneficios.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.controledebeneficios.builder.BeneficiadoraBuilder;
import br.com.controledebeneficios.model.Beneficiadora;
import br.com.controledebeneficios.model.TipoBeneficio;

public class BeneficiadoraServiceTest extends DataBaseTestCase {

	private BeneficiadoraService service;
	
	private Beneficiadora aleloAlimentacao;
	private Beneficiadora aleloRefeicao;
	private Beneficiadora unimed;
	private Beneficiadora uniodonto;
	
	private Integer idValidoUnimed;
	
	@Before
	public void setUp() {
		service = new BeneficiadoraService(manager);
		
		dadosIniciais();
	}
	
	@Test
	public void deveriaPesquisarBeneficiadoraPorId() {
		Beneficiadora beneficiadora = service.pesquisarPorId(idValidoUnimed);
		
		assertThat(beneficiadora, notNullValue());
		verificaBeneficiadora(beneficiadora, unimed);
	}

	@Test
	public void deveriaListarTodasBeneficiadoras() {
		assertThat(service.lista().size(), is(4));
	}
	
	@Test
	public void deveriaPesquisarBeneficiadoraPorNome() {
		Beneficiadora beneficiadora = new BeneficiadoraBuilder().comNome("uNI").build();
		List<Beneficiadora> listaBeneficiadora = service.pesquisarBeneficiadora(beneficiadora);
		
		assertThat(listaBeneficiadora.size(), is(2));
	}

	@Test
	public void deveriaPesquisarBeneficiadoraPorTipoBeneficio() {
		Beneficiadora beneficiadora = new BeneficiadoraBuilder().comTipoBeneficio(TipoBeneficio.PLANO_SAUDE).build();
		List<Beneficiadora> listaBeneficiadora = service.pesquisarBeneficiadora(beneficiadora);
		
		assertThat(listaBeneficiadora.size(), is(1));
	}
	
	@Test
	public void deveriaSalvarBeneficiadora() {
		Beneficiadora beneficiadora = new BeneficiadoraBuilder().comNome("Hapvida")
				.comTipoBeneficio(TipoBeneficio.PLANO_SAUDE).build();
		
		service.salva(beneficiadora);

		Beneficiadora beneficiadoraRetornada = service.pesquisarPorId(beneficiadora.getId());
		
		assertThat(service.lista().size(), is(5));
		verificaBeneficiadora(beneficiadoraRetornada, beneficiadora);
	}
	
	@Test
	public void deveriaAtualizarBeneficiadora() {
		Beneficiadora beneficiadora = new BeneficiadoraBuilder().comId(idValidoUnimed).comNome("Unimed Atualizada")
				.comTipoBeneficio(TipoBeneficio.PLANO_ODONTOLOGICO).build();
		
		service.atualiza(beneficiadora);

		Beneficiadora beneficiadoraAtualizada = service.pesquisarPorId(idValidoUnimed);
		
		assertThat(service.lista().size(), is(4));
		verificaBeneficiadora(beneficiadoraAtualizada, beneficiadora);
	}
	
	@Test
	public void deveriaRemoverBeneficiadora() {
		service.deleta(unimed);

		assertThat(service.lista().size(), is(3));
	}
	
	private void verificaBeneficiadora(Beneficiadora actual, Beneficiadora expected) {
		assertThat(actual.getNome(), is(expected.getNome()));
		assertThat(actual.getTipoBeneficio(), is(expected.getTipoBeneficio()));
	}
	
	private void dadosIniciais() {
		manager.createQuery("delete from Beneficiadora").executeUpdate();
		
		aleloAlimentacao = new BeneficiadoraBuilder().comNome("Alelo Alimentação").comTipoBeneficio(TipoBeneficio.VALE_ALIMENTACAO).build();
		aleloRefeicao = new BeneficiadoraBuilder().comNome("Alelo Refeição").comTipoBeneficio(TipoBeneficio.VALE_REFEICAO).build();
		unimed = new BeneficiadoraBuilder().comNome("Unimed").comTipoBeneficio(TipoBeneficio.PLANO_SAUDE).build();
		uniodonto = new BeneficiadoraBuilder().comNome("Uniodonto").comTipoBeneficio(TipoBeneficio.PLANO_ODONTOLOGICO).build();
		
		List<Beneficiadora> beneficiadoras = Arrays.asList(aleloAlimentacao, aleloRefeicao, unimed, uniodonto);
		
		for(Beneficiadora beneficiadora: beneficiadoras) {
			manager.persist(beneficiadora);
		}
		
		idValidoUnimed = unimed.getId();
	}
	
}
