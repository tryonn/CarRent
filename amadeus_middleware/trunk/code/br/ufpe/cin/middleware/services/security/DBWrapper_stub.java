package br.ufpe.cin.middleware.services.security;

import br.ufpe.cin.middleware.proxies.Stub_abstract;

public class DBWrapper_stub extends Stub_abstract implements DBWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public DBWrapper_stub(String host, int port) {
		super("DBWrapper",host,port);
	}
	
	public boolean validateLogin(String login, String password) {
		return (Boolean)this.process("validateLogin", login,password);
	}

}
