package br.com.controledebeneficios.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.controledebeneficios.model.Usuario;

@Controller
public class UsuarioController {

	private final Result result;
	private final Validator validator;

	@Inject
	public UsuarioController(Result result, Validator validator) {
		this.result = result;
		this.validator = validator;
	}

	/**
	 * @deprecated CDI eyes only
	 */
	public UsuarioController() {
		this(null, null);
	}
	
	@Path("/index")
	public void index() {
		result.include("success", "VRaptor is Working!");
	}
	
	@Post("/pessoa/adiciona")
	public void add(Usuario usuario) {
		validator.addIf(usuario.getLogin() == null, new SimpleMessage("login", "O login deve ser preenchido"));
		validator.onErrorRedirectTo(IndexController.class).index();

		result.include("success", "Incluído com sucesso.");
		
		result.redirectTo(IndexController.class).index();
	}
	
}
