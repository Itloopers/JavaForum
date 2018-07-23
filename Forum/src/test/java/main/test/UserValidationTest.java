package main.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.db.utils.ConnectionToDB;
import main.utils.UserAccountUtils;

public class UserValidationTest extends UserAccountUtils {

	ConnectionToDB con;
	
	@Before
	public void setUp() {
		con = new ConnectionToDB();
	}
	
	@After
	public void closeConnection() throws Exception {
		con.close();
	}

	@Test
	public void shouldReturnTrue() {
		String username = "michal";
		String password = "password";
		if(UserAccountUtils.getUserAccount(username, password)!=null)
			assertTrue(UserAccountUtils.isUserValid("Michal", "password"));
	}

	@Test
	public void shouldReturnFalse() {
		assertFalse(UserAccountUtils.isUserValid("userJakis", "haslo"));
	}

	@Test
	public void wrongUsername() {
		assertFalse(UserAccountUtils.isUserValid("Mil", "password"));
	}

	@Test
	public void wrongPassword() {
		assertFalse(UserAccountUtils.isUserValid("Michal", "haslo"));
	}

	@Test
	public void emptyFields() {
		assertFalse(UserAccountUtils.isUserValid("", ""));
	}
}
