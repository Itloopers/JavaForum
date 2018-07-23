package main.db.utils;

import java.time.LocalDateTime;

import javax.persistence.EntityTransaction;

import main.db.entity.Post;
import main.db.entity.Subject;
import main.db.entity.UserAccount;
import main.db.entity.UserTitle;
import main.utils.SubjectUtils;

public class MainForum extends QueryUtils2 {

	public static void main(String[] args) {
		// Example data to database.
		// First start

		UserAccount user1 = new UserAccount();
		Subject subject = new Subject();
		Post post = new Post();

		user1 = new UserAccount("TestUser", "password", UserTitle.USER);
		post.setAuthor(user1);
		post.setSubject(subject);
		post.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor"
				+ " incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis "
				+ " exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
				+ "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum "
				+ "dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non "
				+ "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		LocalDateTime dateTime = LocalDateTime.now();
		post.setDateTime(dateTime);
		subject.setName("Lorem ipsum");
		subject.setNumberOfPosts(1);
		subject.setSubjectAuthor(user1);

		persist(user1, subject, post);
/*		
		Subject nowy = new Subject();
		nowy.setName("nowy312");
		nowy.setNumberOfPosts(999);
		nowy.setSubjectAuthor(null);
		
		persist(nowy);*/
		

		
	}

}
