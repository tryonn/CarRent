package br.ufpe.cin.middleware.namingService.exceptions;

public class InvalidNameException extends NamingServiceException {

	private static final long serialVersionUID = 1L;
	
	public InvalidNameException() {
		super("Name inv�lido: n�o cont�m nenhum NameComponent");
	}
}
