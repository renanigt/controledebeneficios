package br.com.controledebeneficios.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import br.com.controledebeneficios.builder.BeneficiadoraBuilder;
import br.com.controledebeneficios.model.Beneficiadora;
import br.com.controledebeneficios.model.TipoBeneficio;

public class BeneficiadoraServiceTest extends DataBaseTestCase {

	private BeneficiadoraService service;
	
	private Beneficiadora aleloAlimentacao;
	private Beneficiadora aleloRefeicao;
	private Beneficiadora unimed;
	private Beneficiadora uniodonto;
	
	private Integer idValido;
	
	@Before
	public void setUp() {
		service = new BeneficiadoraService(manager);
		
		dadosIniciais();
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
		
		idValido = unimed.getId();
	}
	
}
