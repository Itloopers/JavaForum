package main.utils;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

public class SubjectUtils extends QueryUtils {

	public static void create(String subjectName, String content, UserAccount user) {
		Subject subject = new Subject();
		Post post = new Post();
		post.setAuthor(user);
		post.setContent(content);
		post.setDateTime(LocalDateTime.now());
		post.setSubject(subject);
		subject.setName(subjectName);
		subject.setSubjectAuthor(user);
		subject.setNumberOfPosts(1);

		persist(subject, post);
	}

	//
	protected static Subject getSubject(String subjectName) {

		Subject subject = null;
		try (ConnectionToDB connection = new ConnectionToDB()) {
			CriteriaBuilder criteriaBuilder = connection.entityManager.getCriteriaBuilder();
			CriteriaQuery<Subject> createQuery = criteriaBuilder.createQuery(Subject.class);
			Root<Subject> fromSubject = createQuery.from(Subject.class);
			Path<String> subName = fromSubject.get("name");
			CriteriaQuery<Subject> where = createQuery.select(fromSubject)
					.where(criteriaBuilder.equal(subName, subjectName));
			TypedQuery<Subject> typedQuery = connection.entityManager.createQuery(where);
			subject = typedQuery.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subject;
		// return getSubjectList().stream().filter(sub ->
		// sub.getName().equals(subjectName)).iterator().next();

	}

	public static List<Subject> getSubjectList() {

		// TODO Order by post date.

		TypedQuery<Subject> typedQuery = null;
		List<Subject> resultList = null;
		try (ConnectionToDB connection = new ConnectionToDB()) {
			CriteriaBuilder criteriaBuilder = connection.entityManager.getCriteriaBuilder();
			CriteriaQuery<Subject> createQuery = criteriaBuilder.createQuery(Subject.class);
			Root<Subject> fromSubject = createQuery.from(Subject.class);
			Path<Integer> orderByNumberOfPosts = fromSubject.get("numberOfPosts");
			Order postDesc = criteriaBuilder.desc(orderByNumberOfPosts);
			createQuery.select(fromSubject).orderBy(postDesc);
			typedQuery = connection.entityManager.createQuery(createQuery);
			resultList = typedQuery.getResultList();
			resultList.stream().forEach(sub -> sub.setNumberOfPosts(countPost(sub)));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		/*
		 * select * from subject s left join post p on p.subject_id=s.id order by
		 * datetime desc select s.id from public.subject s, public.post p where
		 * p.subject_id=s.id order by datetime desc Path<Integer> id =
		 * fromSubject.get("id"); Root<Post> fromPost = createQuery.from(Post.class);
		 * Path<Integer> subject_id = fromPost.get("subject_id");
		 * 
		 * 
		 * Path<Object> dateTime = fromPost.get("dateTime"); Order desc =
		 * criteriaBuilder.desc(dateTime);
		 * createQuery.select(fromSubject).where(criteriaBuilder.equal(subject_id,
		 * id)).orderBy(desc); TypedQuery<Subject> typedQuery =
		 * entityManager.createQuery(createQuery);
		 * 
		 * 
		 * Query createQuery2 =
		 * entityManager.createQuery("select Subject from Subject s\r\n" +
		 * "		left join Post p on p.subject_id=s.id\r\n" +
		 * "		order by dateTime desc");
		 */


		return resultList;
	}

	private static int countPost(Subject sub) {
		return sub.getPostsList().size();
	}

}
