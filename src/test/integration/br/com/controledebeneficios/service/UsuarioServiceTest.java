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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.controledebeneficios.model.Usuario;

@RunWith(Arquillian.class)
public class UsuarioServiceTest {

	private static EntityManager manager;
	private static EntityManagerFactory factory;
	private static EntityTransaction transaction;
	
	private UsuarioService service;
	
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
	}
	
	@Before
	public void setUp() {
		deleteData();
		startTransaction();
		
		service = new UsuarioService(manager);
	}

	@Test
	public void deveriaSalvarUsuario() {
		Usuario usuario = createUsuario();
		service.salvar(usuario);
		
		commitTransaction();
		
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
	
	
	private void startTransaction() {
		transaction.begin();
	}

	private void deleteData() {
		startTransaction();
		manager.createQuery("delete from Usuario").executeUpdate();
		commitTransaction();
	}

	private void commitTransaction() {
		transaction.commit();
	}
	
}
