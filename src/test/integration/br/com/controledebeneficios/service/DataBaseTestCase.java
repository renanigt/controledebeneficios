package br.com.controledebeneficios.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

public class DataBaseTestCase {

	protected EntityManager manager;
	private static EntityManagerFactory factory;
	private EntityTransaction transaction;
	
	static {
		factory = Persistence.createEntityManagerFactory("default-test");
	}
	
	@Before
	public void beforeDataBase() {
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		
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
	}
	
	@AfterClass
	public static void closeFactory() {
		if(factory.isOpen()) {
			factory.close();
		}
	}
	
	private void startTransaction() {
		transaction.begin();
	}
	
}
