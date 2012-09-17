package br.ufpe.cin.amadeus.system.facade;

import java.util.List;

import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.access_control.user.UserDAO;
import br.ufpe.cin.amadeus.system.human_resources.profile.Profile;
import br.ufpe.cin.amadeus.system.util.dao.DAOFactory;
import br.ufpe.cin.amadeus.util.Constants;


public class AccessControlController {

	UserDAO userRepository = (UserDAO) DAOFactory.DEFAULT.createDAO(User.class);
	HumanResourcesController humanResourceController = new HumanResourcesController();
	
	public User searchUser(int id){
		return userRepository.findById(id, false);
	}
	
	public User searchUserByLogin(String login){
		User usuario = new User();
		usuario.setLogin(login);
		List<User> usuarios = userRepository.findByExample(usuario);
		if(usuarios.isEmpty()){
			return null;
		} else {
			return usuarios.get(0);
		}
	}
	
	public User searchUserByEmail(String email){
		
		List<User> usuarios = userRepository.findByEmail(email);
		if(usuarios.isEmpty()){ 
			return null;
		} else {
			return usuarios.get(0);
		}
	}
	
	public User insertUser(User user) {
		if (user.getProfile() == null) {
			Profile profile = humanResourceController.searchProfileByConstant(Constants.STUDENT);
			user.setProfile(profile);
		}
		user = userRepository.makePersistent(user);
//		HumanResourcesController hr = new HumanResourcesController();
//		hr.insertDefaultImage(user.getPerson());
		return user;
	}
	
	public void removeUser(User user){
		userRepository.makeTransient(user);
	}
	
	public User updateUser(User user){
		return userRepository.makePersistentU(user);
	}
	
	public List<User> listUsers(){
		return userRepository.findAll();
	}
	
	public User searchUserByPersonId(int personId){
		return userRepository.findUserByPersonId(personId);
	}
	

	
	

}
