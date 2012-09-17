package br.ufpe.cin.amadeus.system.access_control.user;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;


public class UserHibernateDAO 
       extends GenericHibernateDAO <User, Integer> 
       implements UserDAO {

	public UserHibernateDAO() {
		super(User.class);
	}
	
	
	 public User makePersistentU(User entity) {
		Session session = getSession();
		Transaction t = session.beginTransaction(); // TODO rever transações a
													// esse nível
		try {
			session.update(entity);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		}
		return entity;
	}
	 
	 public List<User> findByEmail(String email){
		 Session session = getSession();
		 List<User> results = session.createSQLQuery("SELECT {u.*} FROM AMADEUSUSER {u} WHERE u.sqUser in (SELECT sqPerson from PERSON p where p.email = '" 
				 + email + "')").addEntity("u",User.class).list();
		 return results;
	 }


	public User findUserByPersonId(int id) {
		Session session = getSession();
		User result = (User)session.createSQLQuery("SELECT {u.*} FROM AMADEUSUSER {u} WHERE u.sqperson = "+ id).addEntity("u",User.class).uniqueResult();
		return result;
	}
}
