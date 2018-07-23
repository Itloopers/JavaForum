package main.utils;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import main.db.entity.Post;
import main.db.entity.Subject;
import main.db.entity.UserAccount;
import main.db.utils.ConnectionToDB;

public class PostUtils extends QueryUtils {

	public static void delete(Long postId) {
		Post post;
		try (ConnectionToDB connection = new ConnectionToDB()) {
			connection.entityManager.getTransaction().begin();
			post = connection.entityManager.find(Post.class, postId);
			removePost(connection.entityManager, post);
			connection.entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void removePost(EntityManager em, Post post) {
		em.remove(post);
	}

	public static void create(String subjectName, String content, UserAccount user) {
		Subject subject = SubjectUtils.getSubject(subjectName);
		Post post = new Post();
		post.setAuthor(user);
		post.setContent(content);
		post.setDateTime(LocalDateTime.now());
		post.setSubject(subject);
		persist(post);
//		TODO post count in subjects
/*		try(ConnectionToDB con = new ConnectionToDB()){
			Post post = new Post();
			con.entityManager.getTransaction().begin();
			Query query = con.entityManager.createNamedQuery("getSubByName");
			query.setParameter("subname", subjectName);
			Subject singleResult = (Subject) query.getSingleResult();
			post.setAuthor(user);
			post.setContent(content);
			post.setDateTime(LocalDateTime.now());
			post.setSubject(singleResult);
			singleResult.setNumberOfPosts(9999);
			con.entityManager.persist(post);
			con.entityManager.persist(singleResult);
			con.entityManager.getTransaction().commit();
		}	*/	
	}

	public static List<Post> getPostList(String subjectName) {
		List<Post> resultList;
		TypedQuery<Post> query = null;

		try (ConnectionToDB connection = new ConnectionToDB()) {
			CriteriaBuilder criteriaBuilder = connection.entityManager.getCriteriaBuilder();
			CriteriaQuery<Post> createQuery = criteriaBuilder.createQuery(Post.class);
			Root<Post> fromPost = createQuery.from(Post.class);
			Path<String> path = fromPost.get("subject").get("name");
			Path<Object> path2 = fromPost.get("dateTime");
			Order asc = criteriaBuilder.asc(path2);
			createQuery.select(fromPost).where(criteriaBuilder.equal(path, subjectName)).orderBy(asc);
			query = connection.entityManager.createQuery(createQuery);
			resultList = query.getResultList();
		}
		catch (Exception e) {
			resultList = null;
			e.printStackTrace();
		}
		return resultList;
	}

}
