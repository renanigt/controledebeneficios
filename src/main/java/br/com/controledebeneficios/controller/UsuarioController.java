package br.com.controledebeneficios.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.controledebeneficios.service.UsuarioService;

public class UsuarioController {

	private Result result;
	
	private UsuarioService service;
	
	@Inject
	public UsuarioController(Result result, UsuarioService service) {
		this.result = result;
		this.service = service;
	}
	
	/**
	 * @deprecated CDI eyes only
	 */
	public UsuarioController() {
	}

	@Get("/usuario")
	public void index() {
		result.include("usuarios", service.lista());
	}
	
}
