package main.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.db.utils.ConnectionToDB;
import main.utils.UserAccountUtils;

public class UserRegistrationTest extends UserAccountUtils{
	
	static ConnectionToDB con;
	
	@BeforeClass
	public static void setUp() {
		con = new ConnectionToDB();
	}
	
	@AfterClass
	public static void closeConnection() throws Exception {
		con.close();
	}
	
	@Test
	public void test() {
		assertFalse(UserAccountUtils.registerUser("Adam", "pass", "xxxxxxx"));
	}
	@Test
	public void test1() {
		assertFalse(UserAccountUtils.registerUser("", "xxxxxxx", "pass"));
	}
	@Test
	public void test2() {
		assertFalse(UserAccountUtils.registerUser("Adam", "", "pass"));
	}

	@Test
	public void shouldReturnTrue() {
		
		String username = "Adam";
		String password = "pass";
		if(UserAccountUtils.getUserAccount(username, password)!=null)
				assertFalse(UserAccountUtils.registerUser(username,password,password));
		else
			assertTrue(UserAccountUtils.registerUser(username,password,password));
	}

}
