package br.ufpe.cin.amadeus.system.academic.module.homework;

import java.util.List;

import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class HomeworkHibernateDAO extends GenericHibernateDAO<Homework, Integer> implements HomeworkDAO {
	
	public HomeworkHibernateDAO(){
		super(Homework.class);
	}

	public int searchTotalUserHomeworks(User user, Role studentRole) {
		List<Homework> results = getSession()
				.createSQLQuery(
						"select {h.*} from homework {h} where sqmodule in (select sqmodule from course_module where sqcourse in (select sqcourse from person_role_course where sqrole = "
								+ studentRole.getId()
								+ " and sqperson = "
								+ user.getPerson().getId() + " ))").addEntity(
						"h", Homework.class).list();
		return results.size();
	}

}
