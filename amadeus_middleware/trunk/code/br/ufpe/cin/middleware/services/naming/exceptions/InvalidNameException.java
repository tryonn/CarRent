package br.ufpe.cin.middleware.services.naming.exceptions;

public class InvalidNameException extends NamingServiceException {

	private static final long serialVersionUID = 1L;
	
	public InvalidNameException() {
		super("Name inválido: não contém nenhum NameComponent");
	}
}
