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
		this(null, null, null);
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
		boolean loginExistente = service.pesquisaPorLogin(usuario.getLogin()) != null ? true : false;
		
		validator.addIf(isNullOrEmpty(usuario.getLogin()), new SimpleMessage("Login", "O Login não pode ser vazio."));
		validator.addIf(isNullOrEmpty(usuario.getSenha()), new SimpleMessage("Senha", "A Senha não pode ser vazia."));
		validator.addIf(loginExistente, new SimpleMessage("Login", "O Login já existe."));

		validator.onErrorRedirectTo(this).novo();
		
		service.salva(usuario);
		
		result.include("sucesso", "Usuário adicionado com sucesso.");
	}

	@Get("/usuario/deleta/{id}")
	public void delete(Integer id) {
		Usuario usuario = service.pesquisaPorId(id);
		
		if(usuario != null) {
			service.delete(usuario);
			result.include("sucesso", "Usuário removido com sucesso.");
		} else {
			result.include("erro", "Usuário inexistente.");
		}

		result.redirectTo(this).index();
	}
	
}
