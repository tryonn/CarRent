package br.ufpe.cin.amadeus.system.academic.module.homework.delivery;

import java.util.List;

import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class DeliveryHibernateDAO extends GenericHibernateDAO<Delivery, Integer> implements DeliveryDAO {
	
	public DeliveryHibernateDAO(){
		super(Delivery.class);
	}

	public int getNumberOfDoneHomeworks(User user, Role role) {
		List<Delivery> results = getSession()
				.createSQLQuery(
						"select {d.*} from delivery {d} where sqperson = "
								+ user.getPerson().getId()
								+ " and sqhomework in (select sqhomework from homework where sqmodule in (select sqmodule from course_module where sqcourse in (select sqcourse from person_role_course where sqperson = "
								+ user.getPerson().getId() + " and sqrole = "
								+ role.getId() + " )))").addEntity("d",
						Delivery.class).list();
		return results.size();
	}

}
