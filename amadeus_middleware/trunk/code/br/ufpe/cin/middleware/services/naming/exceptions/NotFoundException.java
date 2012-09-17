package br.ufpe.cin.middleware.services.naming.exceptions;

import br.ufpe.cin.middleware.services.naming.Name;

public class NotFoundException extends NamingServiceException {

	
	private static final long serialVersionUID = 1L;
	
	ENotFoundReason why;
	Name rest_of_name;
	
	public NotFoundException() {
		super("Não foi encontrado um objeto relacionado a um Name");
	}
	
	public NotFoundException(ENotFoundReason reason) {
		this();
		this.why = reason;
	}

	public NotFoundException(Name name) {
		this();
		this.rest_of_name = name;
	}
}
