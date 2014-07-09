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
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;

import br.com.controledebeneficios.model.Usuario;

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
	}

	@Rule
	public ExternalResource resource = new ExternalResource() {
		@Override
	    protected void before() throws Throwable {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			
			service = new UsuarioService(manager);
			
			deleteData();
			startTransaction();
	    };

	    @Override
	    protected void after() {
	    	if(transaction.isActive()) {
	    		transaction.rollback();
	    	}
	    	
	    	if(manager.isOpen()) {
	    		manager.close();
	    	}
	    	
	    	if(factory.isOpen()) {
	    		factory.close();
	    	}
	    };
	    
	    private void startTransaction() {
			transaction.begin();
		}

		private void deleteData() {
			startTransaction();
			manager.createQuery("delete from Usuario").executeUpdate();
			transaction.commit();
		}
	};
	
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
	
}
