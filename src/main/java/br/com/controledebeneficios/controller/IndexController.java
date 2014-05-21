package br.com.controledebeneficios.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
public class IndexController {

	private Result result;

	@Inject
	public IndexController(Result result) {
		this.result = result;
	}

	/**
	 * @deprecated CDI eyes only
	 */
	public IndexController() {
	}
	
	@Path("/index")
	public void index() {
		result.include("teste", "VRaptor is Working...");
	}
	
}
