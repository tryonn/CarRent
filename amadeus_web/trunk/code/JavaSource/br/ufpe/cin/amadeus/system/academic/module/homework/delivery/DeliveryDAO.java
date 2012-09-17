package br.ufpe.cin.amadeus.system.academic.module.homework.delivery;

import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface DeliveryDAO extends GenericDAO<Delivery, Integer> {

	int getNumberOfDoneHomeworks(User user, Role role);

}
