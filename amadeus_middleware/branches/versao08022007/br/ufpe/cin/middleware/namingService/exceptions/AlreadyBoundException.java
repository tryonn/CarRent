package br.ufpe.cin.middleware.namingService.exceptions;

public class AlreadyBoundException extends NamingServiceException {

	private static final long serialVersionUID = 1L;
	
	public AlreadyBoundException() {
		super("Já existe um objeto atribuído a este Name");
	}
	
}
