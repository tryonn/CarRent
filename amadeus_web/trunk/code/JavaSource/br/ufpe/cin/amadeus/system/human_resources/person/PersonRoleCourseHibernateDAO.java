package br.ufpe.cin.amadeus.system.human_resources.person;

import java.util.List;

import org.hibernate.Session;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;


public class PersonRoleCourseHibernateDAO extends GenericHibernateDAO<PersonRoleCourse, Integer> implements PersonRoleCourseDAO {
	
	public PersonRoleCourseHibernateDAO(){
		super(PersonRoleCourse.class);
	}
	
	public List<PersonRoleCourse> findRegistered(User usr, Course course){
		Session session = getSession();
		Person tempPerson = usr.getPerson();
		List<PersonRoleCourse> results = session.createSQLQuery("SELECT {ppc.*} from PERSON_ROLE_COURSE {ppc} where ppc.sqCourse = "+ 
				course.getId()+" AND ppc.sqperson = "+tempPerson.getId()).addEntity("ppc", PersonRoleCourse.class).list();
		return results;
	}
	
	public List<PersonRoleCourse> findCurso(Course course, Role role){ 
		Session session = getSession();
		List<PersonRoleCourse> results = session.createSQLQuery("SELECT {ppc.*} from PERSON_ROLE_COURSE {ppc} where ppc.sqCourse = "+
				course.getId()+" AND ppc.sqrole = "+role.getId()).addEntity("ppc",PersonRoleCourse.class).list();
		return results;
	}
	
	public List<User> listUsersByCourse(Course course, Role role){
		Session session = getSession();
		List<User> results = session.createSQLQuery("SELECT {u.*} from AMADEUSUSER {u} where u.sqPerson in (SELECT sqPerson from PERSON_ROLE_COURSE "+
											"p WHERE p.sqCourse = "+ course.getId()+" AND p.sqRole = "+ role.getId()+" )").addEntity("u",User.class).list();
		return results;
	}

	public PersonRoleCourse searchPersonRoleCourse(Person person, Course course) {
		Session session = getSession();
		PersonRoleCourse result = (PersonRoleCourse)session.createSQLQuery("SELECT {ppc.*} FROM PERSON_ROLE_COURSE {ppc} WHERE ppc.sqcourse = "+course.getId()+" AND " +
				"ppc.sqperson = "+person.getId()).addEntity("ppc",PersonRoleCourse.class).uniqueResult();
		return result;
	}

}
