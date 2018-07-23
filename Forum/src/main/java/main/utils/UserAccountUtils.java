package main.utils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import main.db.entity.UserAccount;
import main.db.entity.UserTitle;
import main.db.utils.ConnectionToDB;

public class UserAccountUtils extends QueryUtils {

	public static UserAccount getUserAccount(String username, String password) {
		UserAccount user = null;
		try (ConnectionToDB connection = new ConnectionToDB()) {
			CriteriaBuilder criteriaBuilder = connection.entityManager.getCriteriaBuilder();
			CriteriaQuery<UserAccount> createQuery = criteriaBuilder.createQuery(UserAccount.class);
			Root<UserAccount> fromUserAccount = createQuery.from(UserAccount.class);
			Path<String> usernameField = fromUserAccount.get("username");
			Path<String> passwordField = fromUserAccount.get("password");
			Predicate and = criteriaBuilder.and(criteriaBuilder.equal(usernameField, username),
					criteriaBuilder.equal(passwordField, password));
			createQuery.select(fromUserAccount).where(and);

			TypedQuery<UserAccount> typedQuery = connection.entityManager.createQuery(createQuery);
			return user = typedQuery.getSingleResult();
		} catch (Exception e) {
			user = null;
			e.printStackTrace();
		}
		return user;
	}

	public static void setSessionUser(HttpSession session, UserAccount userAccount) {
		session.setAttribute("user", userAccount);
		if (userAccount.getUserTitle().equals(UserTitle.USER)) {
			session.setAttribute("deleteBtn", "style=\"display: none;\"");
		} else {
			// do nothing
		}
	}

	public static boolean registerUser(String username, String password, String password2) {
		if (password.equals(password2) && !password.isEmpty() && !isUserExist(username)) {
			UserAccount user = new UserAccount();
			user.setUsername(username);
			user.setPassword(password);
			user.setUserTitle(UserTitle.USER);

			persist(user);
			return true; // user successful registered
		} else
			return false;
	}

	private static boolean isUserExist(String username) {
		UserAccount user = null;
		TypedQuery<UserAccount> typedQuery = null;
		try (ConnectionToDB connection = new ConnectionToDB()) {
			CriteriaBuilder criteriaBuilder = connection.entityManager.getCriteriaBuilder();
			CriteriaQuery<UserAccount> createQuery = criteriaBuilder.createQuery(UserAccount.class);
			Root<UserAccount> fromUserAccount = createQuery.from(UserAccount.class);
			Path<Object> usernameField = fromUserAccount.get("username");
			createQuery.select(fromUserAccount).where(criteriaBuilder.equal(usernameField, username));
			typedQuery = connection.entityManager.createQuery(createQuery);
			user = typedQuery.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (user != null);
	}

	public static boolean isUserValid(String username, String password) {
		UserAccount user = getUserAccount(username, password);
		if (user == null)
			return false;
		else if (user.getUsername().equals(username) && user.getPassword().equals(password))
			return true;
		else
			return false;
	}
}
