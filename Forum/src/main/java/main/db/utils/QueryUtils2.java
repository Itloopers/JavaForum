package main.db.utils;

abstract class QueryUtils2 extends ConnectionToDB {

	protected static void persist(Object... objects) {
		executeInTransaction("persist", objects);
	}

	protected static void remove(Object... objects) {
		executeInTransaction("remove", objects);
	}

	private static void executeInTransaction(String opt, Object... objects) {
		try (ConnectionToDB connection = new ConnectionToDB()) {
			connection.entityManager.getTransaction().begin();

			switch (opt) {
			case "remove":
				for (Object obj : objects) {
					connection.entityManager.remove(obj);
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
