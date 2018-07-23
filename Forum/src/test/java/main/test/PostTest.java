package main.test;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.db.entity.Post;
import main.db.entity.Subject;
import main.db.entity.UserAccount;
import main.db.entity.UserTitle;
import main.utils.PostUtils;

public class PostTest extends PostUtils {
	
	static Subject sub;
	static UserAccount user;
	
	@Before
	public void setUp() {
		sub = new Subject();
		user = new UserAccount("Test_User","password",UserTitle.ADMIN);
		
		sub.setName("TestSubject");
		sub.setNumberOfPosts(1);
		sub.setSubjectAuthor(user);
	}
	
	@After
	public void close() {
		
	}
	
	@Test
	public void canCreateNewPost1() {
		//PostUtils.create(sub.getName(), "some content", user);
		//PostUtils.getPostList(sub.getName()).contains(new Post().setAuthor(author);)
	}
	@Test
	public void canCreateNewPost2() {
		Post post = new Post();
		post.setAuthor(user);
		post.setContent("Content 2 post");
		post.setDateTime(LocalDateTime.now());
		post.setSubject(sub);
		assertTrue(PostUtils.getPostList(sub.getName()).contains(post));
	}

}
