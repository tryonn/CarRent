package br.ufpe.cin.middleware.namingService.exceptions;

public class InvalidAddressException extends NamingServiceException {

	private static final long serialVersionUID = 1L;
	
	public InvalidAddressException() {
		super("Endereço inválido");
	}
}
