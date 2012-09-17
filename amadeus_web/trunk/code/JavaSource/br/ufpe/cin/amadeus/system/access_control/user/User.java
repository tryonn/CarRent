package br.ufpe.cin.amadeus.system.access_control.user;

import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.human_resources.profile.Profile;

public class User {

	private int id;
	private Person 	person;
	private String 	login;
	private String 	password;
	private Profile profile;

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person pessoa) {
		this.person = pessoa;
	}
	
	public boolean checkPassword(String senha) {
		boolean result = false;
		if (senha != null) {
			if (senha.equals(this.password)) {
				result = true;
			}
		}
		return result;
	}
	
	public void setPassword(String senha) {
		this.password = senha;
	}

	public String getPassword() {
		return password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile perfil) {
		this.profile = perfil;
	}
	
}
