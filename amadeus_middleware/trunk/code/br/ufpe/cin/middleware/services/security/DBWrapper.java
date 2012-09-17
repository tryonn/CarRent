package br.ufpe.cin.middleware.services.security;

import br.ufpe.cin.middleware.services.naming.RemoteInterface;

public interface DBWrapper extends RemoteInterface {
	
	public boolean validateLogin(String login, String password);
	
}
