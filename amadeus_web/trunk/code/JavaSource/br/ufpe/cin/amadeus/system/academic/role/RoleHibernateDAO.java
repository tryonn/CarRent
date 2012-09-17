package br.ufpe.cin.amadeus.system.academic.role;

import java.util.List;

import org.hibernate.Session;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;


public class RoleHibernateDAO extends GenericHibernateDAO<Role, Integer>
								implements RoleDAO{
	
	public RoleHibernateDAO(){
		super(Role.class);
	}
	
	public int searchRoleByUser(User user, Course course){
		Session session = getSession();
		List<Role> result = session.createSQLQuery("Select {r.*} from Role {r} where r.sqrole = " +
			"(select sqrole from person_role_course where sqcourse = "+course.getId()+ " and sqperson = "+user.getPerson().getId()+")"+
			")").addEntity("r",Role.class).list();
		
		if(result == null || result.size() == 0){
			return -1;
		} else {
			return result.get(0).getConstantRole();
		}
	}

}
