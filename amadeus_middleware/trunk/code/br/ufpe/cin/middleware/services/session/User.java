package br.ufpe.cin.middleware.services.session;

import java.io.Serializable;

/**
 * Caso seja alterado, não va alterar na interfac SessionService
 * @author amadeus
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	String login;
	String senha;

	public User(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			return ((user.login.equalsIgnoreCase(this.login))&&(user.senha.equals(this.senha)));
		}
		return false;
	}
	
}
