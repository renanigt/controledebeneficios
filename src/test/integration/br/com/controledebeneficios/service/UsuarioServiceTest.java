package br.com.controledebeneficios.service;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.jpa.EntityManagerFactoryCreator;
import br.com.controledebeneficios.model.Usuario;

@RunWith(Arquillian.class)
public class UsuarioServiceTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(Usuario.class)
				.addClass(UsuarioService.class)
				.addClass(EntityManagerFactoryCreator.class)
				.addAsResource(new File("src/test/resources/META-INF/persistence-test.xml"),
						"persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
}
