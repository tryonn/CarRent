package br.ufpe.cin.amadeus.system.human_resources.profile;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;


public class ProfileHibernateDAO extends GenericHibernateDAO <Profile,Integer>
	implements ProfileDAO{
	
	public ProfileHibernateDAO(){
		super(Profile.class);
	}
	
	 public Profile findById(int id, boolean lock) {
		Session session = getSession();
		Profile entity = null;
		Transaction tx = session.beginTransaction();

		if (session.get(getPersistentClass(), id) != null) {
			if (lock) {
				entity = (Profile) session.load(getPersistentClass(), id,
						LockMode.UPGRADE);
			} else {
				entity = (Profile) session.load(getPersistentClass(), id);
			}
		}

		tx.commit();
		return entity;
	}
	 
	 public List<Profile> searchProfileByConstant(int constant){
		 Session session = getSession();
		 List<Profile> results = session.createSQLQuery("SELECT {p.*} FROM profile {p} WHERE p.idProfile = '" 
				 + constant + "')").addEntity("p",Profile.class).list();
		 return results;
	 }

}
