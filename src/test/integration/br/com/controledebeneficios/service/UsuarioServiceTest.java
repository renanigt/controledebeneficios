package br.com.controledebeneficios.service;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.controledebeneficios.model.Usuario;
import br.com.controledebeneficios.rule.TransactionRule;

@RunWith(Arquillian.class)
public class UsuarioServiceTest {

	private static EntityManager manager;
	private static EntityManagerFactory factory;
	private static EntityTransaction transaction;
	
	private static UsuarioService service;
	
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addAsResource(new File("src/test/resources/META-INF/persistence-test.xml"),
						"META-INF/persistence.xml");
	}

	@BeforeClass
	public static void configure() {
		factory = Persistence.createEntityManagerFactory("default-test");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();

		service = new UsuarioService(manager);
	}
	
	@Rule
	public TransactionRule transactionRule = new TransactionRule(transaction, manager);
	
	@Test
	public void deveriaSalvarUsuario() {
		Usuario usuario = createUsuario();
		service.salvar(usuario);
		
		Assert.assertEquals(1, service.lista().size());
	}

	private Usuario createUsuario() {
		Usuario usuario = new Usuario();
		usuario.setLogin("renanigt");
		usuario.setSenha("teste123");
		
		return usuario;
	}
	
	@AfterClass
	public static void closeEntityManager() {
		manager.close();
		factory.close();
	}
	
}
