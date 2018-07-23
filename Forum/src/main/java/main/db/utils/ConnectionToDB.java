package main.db.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionToDB implements AutoCloseable {

	public EntityManager entityManager;
	public EntityManagerFactory entityManagerFactory;

	public ConnectionToDB() {
		if(entityManager == null && entityManagerFactory == null) {
			openConnection();
		}
	}

	public void openConnection() {
		entityManagerFactory = Persistence.createEntityManagerFactory("manager2");
		entityManager = entityManagerFactory.createEntityManager();
	}

	public void close() {
/*		entityManager.close();
		entityManagerFactory.close();
		entityManager = null;
		entityManagerFactory = null;*/
	}

}
