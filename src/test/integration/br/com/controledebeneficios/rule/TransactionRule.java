package br.com.controledebeneficios.rule;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TransactionRule implements TestRule {

	private final EntityManager manager;
	private final EntityTransaction transaction;
	
	public TransactionRule(EntityTransaction transaction, EntityManager manager) {
		this.transaction = transaction;
		this.manager = manager;
	}
	
	@Override
	public Statement apply(final Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				try {
					deleteData();
					startTransaction();
					
					base.evaluate();
				} finally {
					commitTransaction();
				}
			}
		};
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
