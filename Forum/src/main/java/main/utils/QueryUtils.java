package main.utils;

import main.db.utils.ConnectionToDB;

abstract class QueryUtils extends ConnectionToDB {

	protected static void persist(Object... objects) {
		executeInTransaction("persist", objects);
	}

	protected static void refresh(Object... objects) {
		//TODO
	}

	private static void executeInTransaction(String opt, Object... objects) {
		try (ConnectionToDB connection = new ConnectionToDB()) {
			connection.entityManager.getTransaction().begin();
			switch (opt) {
			
			 case "refresh": 
				 for (Object obj : objects) {
					 connection.entityManager.refresh(obj); 
					 }
				 break;
			 

			case "persist":
				for (Object obj : objects) {
					connection.entityManager.persist(obj);
				}
				break;
			}
			connection.entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
