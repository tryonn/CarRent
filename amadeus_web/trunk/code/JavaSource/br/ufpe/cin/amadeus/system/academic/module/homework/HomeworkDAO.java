package br.ufpe.cin.amadeus.system.academic.module.homework;

import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface HomeworkDAO extends GenericDAO<Homework, Integer>{

	int searchTotalUserHomeworks(User user, Role studentRole);

}
