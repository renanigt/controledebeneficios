package br.com.controledebeneficios.controller;

import javax.inject.Inject;

import com.google.common.base.Strings;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.controledebeneficios.model.Beneficiadora;
import br.com.controledebeneficios.service.BeneficiadoraService;

@Controller
public class BeneficiadoraController {

	private Result result;
	private Validator validator;
	
	private BeneficiadoraService service;
	
	@Inject
	public BeneficiadoraController(Result result, Validator validator, BeneficiadoraService service) {
		this.result = result;
		this.service = service;
		this.validator = validator;
	}
	
	/**
	 * @deprecated CDI eyes only
	 */
	public BeneficiadoraController() {
		this(null, null, null);
	}
	
	@Get("/beneficiadora")
	public void index() {
		result.include("beneficiadoras", service.lista());
	}

	@Post("/beneficiadora/novo/salvar")
	public void adiciona(Beneficiadora beneficiadora) {
		validaBeneficiadora(beneficiadora);
		validator.onErrorForwardTo(this).index();
		
		service.salva(beneficiadora);
		
		result.include("sucesso", "Beneficiadora adicionada com sucesso.");
	}

	@Get("/beneficiadora/deleta/{id}")
	public void delete(Integer id) {
		Beneficiadora beneficiadora = service.pesquisarPorId(id);
		
		if(beneficiadora != null) {
			service.deleta(beneficiadora);
			result.include("sucesso", "Beneficiadora removida com sucesso.");
		} else {
			result.include("erro", "Beneficiadora inexistente.");
		}
		
		
	}

	@Post("/beneficiadora/novo/atualizar")
	public void atualiza(Beneficiadora beneficiadora) {
		validaBeneficiadora(beneficiadora);
		validator.onErrorForwardTo(this).index();
		
		service.atualiza(beneficiadora);
		
		result.include("sucesso", "Beneficiadora atualizada com sucesso.");
	}

	private void validaBeneficiadora(Beneficiadora beneficiadora) {
		validator.addIf(Strings.isNullOrEmpty(beneficiadora.getNome()), new SimpleMessage("Nome", "Nome não pode ser vazio."));
		validator.addIf(beneficiadora.getTipoBeneficio() == null, new SimpleMessage("Tipo de Beneficio", "Tipo de Beneficio não pode ser vazio."));
	}
	
}
