package br.ufpe.cin.amadeus.system.human_resources.person;

import java.util.List;

import org.hibernate.Session;

import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class PersonHibernateDAO extends GenericHibernateDAO<Person, Integer> implements PersonDAO{
	
	public PersonHibernateDAO(){
		super(Person.class);
	}

	public List<Person> getPossibleTeachers() {
		Session session = getSession();
		List<Person> results = session
				.createSQLQuery(
						"select {p.*} from person {p} where p.sqperson in (select sqperson from request where instatus like 'ag' and in_teaching = true)")
				.addEntity("p", Person.class).list();
		return results;
	}

	public List<Person> getPossibleTeacherAssistants(User user, Role teacherRole) {
		Session session = getSession();
		List<Person> results = session.createSQLQuery(
				"select {p.*} from person {p} where p.sqperson "
						+ "in (select " + "sqperson from request "
						+ "where in_teaching = false and instatus like "
						+ "'ag' and sqcourse in"
						+ " (select sqcourse from person_role_course"
						+ " where sqrole =" + teacherRole.getId()
						+ "  and sqperson =" + user.getPerson().getId() + "))")
				.addEntity("p", Person.class).list();
		return results;
	}

}
