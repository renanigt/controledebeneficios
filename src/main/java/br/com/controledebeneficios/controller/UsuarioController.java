package br.com.controledebeneficios.controller;

import static com.google.common.base.Strings.isNullOrEmpty;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.controledebeneficios.model.Usuario;
import br.com.controledebeneficios.service.UsuarioService;

@Controller
public class UsuarioController {

	private Result result;
	private Validator validator;
	
	private UsuarioService service;
	
	@Inject
	public UsuarioController(Result result, Validator validator, UsuarioService service) {
		this.result = result;
		this.validator = validator;
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

	@Get("/usuario/novo")
	public void novo() {
		
	}
	
	@Post("/usuario/novo/salvar")
	public void adiciona(Usuario usuario) {
		validator.addIf(isNullOrEmpty(usuario.getLogin()), new SimpleMessage("Login", "O Login não pode ser vazio."));
		validator.addIf(isNullOrEmpty(usuario.getSenha()), new SimpleMessage("Senha", "A Senha não pode ser vazia."));

		validator.onErrorRedirectTo(this).novo();
		
		service.salvar(usuario);
		
		result.include("sucesso", "Usuário adicionado com sucesso.");
	}
	
}
