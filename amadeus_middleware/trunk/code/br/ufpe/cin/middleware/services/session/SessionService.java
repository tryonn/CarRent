package br.ufpe.cin.middleware.services.session;

import java.io.Serializable;

import br.ufpe.cin.middleware.services.naming.RemoteInterface;

public interface SessionService extends RemoteInterface, Serializable {

	public Session[] getUserbyMicroMundo(String microMundo);
	
	public Session[] getUsers();
	
	public boolean update (Session session) throws Exception;
	
	public boolean create (Session session) throws Exception;
	
	public boolean remove (Session session) throws Exception;
}
