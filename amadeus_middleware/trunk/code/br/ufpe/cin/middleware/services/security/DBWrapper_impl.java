package br.ufpe.cin.middleware.services.security;

import java.util.ArrayList;

import br.ufpe.cin.middleware.services.session.User;

//import br.ufpe.cin.amadeus.system.access_control.user.User;
//import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;


public class DBWrapper_impl implements DBWrapper {
//	AmadeusSystem amadeusSystem = AmadeusSystem.getInstance();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<User> users;

	public boolean validateLogin(String login, String password) {
		
		boolean retorno = false; 
//		User user = this.amadeusSystem.searchUserByLogin(login);
//		if((user == null)||(!user.checkPassword(password))){
//			retorno = false;
//		}
		
		if(this.users != null){
			retorno = this.users.contains(new User(login,password));
		}
		return retorno;
	}
	
	//TODO Retirar isso após a apresentação!
	public void init() {
		this.users = new ArrayList<User>();
		users.add(new User("blbs","teste"));
		users.add(new User("rgo2","rgo2"));
	}
	
	

}
