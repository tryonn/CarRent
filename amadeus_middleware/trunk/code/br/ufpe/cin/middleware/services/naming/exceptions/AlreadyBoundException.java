package br.ufpe.cin.middleware.services.naming.exceptions;

public class AlreadyBoundException extends NamingServiceException {

	private static final long serialVersionUID = 1L;
	
	public AlreadyBoundException() {
		super("J� existe um objeto atribu�do a este Name");
	}
	
}
