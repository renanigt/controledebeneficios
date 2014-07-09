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
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DataBaseTestCase {

	protected EntityManager manager;
	private static EntityManagerFactory factory;
	private EntityTransaction transaction;
	
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addAsResource(new File("src/test/resources/META-INF/persistence-test.xml"),
						"META-INF/persistence.xml");
	}
	
	@Before
	public void beforeDataBase() {
		factory = Persistence.createEntityManagerFactory("default-test");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		
		deleteData();
		startTransaction();
	}

	@After
	public void afterDataBase() {
		if(transaction.isActive()) {
    		transaction.rollback();
    	}
    	
    	if(manager.isOpen()) {
    		manager.close();
    	}
    	
    	if(factory.isOpen()) {
    		factory.close();
    	}
	}
	
	private void startTransaction() {
		transaction.begin();
	}

	private void deleteData() {
		startTransaction();
		manager.createQuery("delete from Usuario").executeUpdate();
		transaction.commit();
	}
	
}
