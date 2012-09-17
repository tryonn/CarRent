package br.ufpe.cin.amadeus.system.access_control.user;

import java.util.List;

import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;


public interface UserDAO extends GenericDAO<User, Integer> {
		
	public List<User> findByEmail(String email);
	public User findUserByPersonId(int id);
	
}
