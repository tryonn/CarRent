package br.ufpe.cin.middleware.namingService.exceptions;

public class AlreadyBoundException extends NamingServiceException {

	private static final long serialVersionUID = 1L;
	
	public AlreadyBoundException() {
		super("J� existe um objeto atribu�do a este Name");
	}
	
}
