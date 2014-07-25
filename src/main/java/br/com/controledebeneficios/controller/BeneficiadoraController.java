package br.com.controledebeneficios.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.controledebeneficios.service.BeneficiadoraService;

public class BeneficiadoraController {

	private Result result;
	
	private BeneficiadoraService service;
	
	@Inject
	public BeneficiadoraController(Result result, BeneficiadoraService service) {
		this.result = result;
		this.service = service;
	}
	
	/**
	 * @deprecated CDI eyes only
	 */
	public BeneficiadoraController() {
		this(null, null);
	}
	
	@Get("/beneficiadora")
	public void index() {
		result.include("beneficiadoras", service.lista());
	}

}
