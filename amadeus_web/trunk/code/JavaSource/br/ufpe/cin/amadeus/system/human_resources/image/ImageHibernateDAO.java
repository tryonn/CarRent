package br.ufpe.cin.amadeus.system.human_resources.image;

import java.util.List;

import org.hibernate.Session;

import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class ImageHibernateDAO extends GenericHibernateDAO<Image, Integer> implements ImageDAO {
	
	public ImageHibernateDAO(){
		super(Image.class);
	}
	
	public Image searchImageByPerson(Person person){
		Image retorno = null;
		Session session = getSession();
		List<Image> results = session.createSQLQuery("SELECT {i.*} from IMAGE {i} WHERE i.sqperson = " +
				person.getId()).addEntity("i",Image.class).list();
		if(!results.isEmpty()){
			retorno = results.get(0);
		}
		return retorno;
	}

}
