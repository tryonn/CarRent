package br.ufpe.cin.amadeus.system.human_resources.resume;

import java.util.List;

import org.hibernate.Session;

import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class ResumeHibernateDAO extends GenericHibernateDAO<Resume, Integer>
		implements ResumeDAO {
	
	public ResumeHibernateDAO(){
		super(Resume.class);
	}
	
	public Resume searchResumeByPerson(Person person){
		Session session = getSession();
		List<Resume> results = session.createSQLQuery("SELECT {r.*} from RESUME {r} WHERE r.sqperson = " +
				person.getId()).addEntity("r",Resume.class).list();
		if(results.isEmpty()){
			session.close();
			return null;
		} else {
			Resume retorno = results.get(0);
			return retorno;
		}
	}

}
