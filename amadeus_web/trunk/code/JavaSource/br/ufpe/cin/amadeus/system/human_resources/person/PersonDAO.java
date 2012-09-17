package br.ufpe.cin.amadeus.system.human_resources.person;

import java.util.List;

import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface PersonDAO extends GenericDAO<Person, Integer> {
	
	public List<Person> getPossibleTeachers();

	public List<Person> getPossibleTeacherAssistants(User user, Role teacherRole);

}
