package br.ufpe.cin.middleware.services.naming.exceptions;

public class InvalidAddressException extends NamingServiceException {

	private static final long serialVersionUID = 1L;
	
	public InvalidAddressException() {
		super("Endere�o inv�lido");
	}
}
