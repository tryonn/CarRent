package br.ufpe.cin.amadeus.system.academic.role;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface RoleDAO extends GenericDAO<Role, Integer> {
	
	public int searchRoleByUser(User user, Course course);

}
