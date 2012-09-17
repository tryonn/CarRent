package br.ufpe.cin.middleware.services.session;

import br.ufpe.cin.middleware.proxies.Stub_abstract;

public class SessionService_stub extends Stub_abstract implements SessionService{


	/**
	 * ID default
	 */
	private static final long serialVersionUID = 1L;
	
	public SessionService_stub(String host, int port) {
		super("SessionService",host,port);
	}

	public boolean create(Session session) {
		return (Boolean) this.process("create", session);
		
	}

	public Session[] getUserbyMicroMundo(String microMundo) {
		return (Session[]) this.process("getUserByMicroMundo", microMundo);
	}

	public Session[] getUsers() {
		return (Session[]) this.process("getUsers");
	}

	public boolean remove(Session session) {
		return (Boolean) this.process("remove", session);
	}

	public boolean update(Session session) {
		return (Boolean)this.process("update", session);
		
	}

}
