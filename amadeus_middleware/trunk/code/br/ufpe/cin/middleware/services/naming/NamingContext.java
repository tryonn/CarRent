package br.ufpe.cin.middleware.services.naming;

import br.ufpe.cin.middleware.services.naming.exceptions.AlreadyBoundException;
import br.ufpe.cin.middleware.services.naming.exceptions.InvalidNameException;
import br.ufpe.cin.middleware.services.naming.exceptions.NotFoundException;

public interface NamingContext {

	public void bind (Name n, RemoteProcess obj) throws NotFoundException, InvalidNameException, AlreadyBoundException;
	public void rebind (Name n, RemoteProcess obj) throws NotFoundException, InvalidNameException;
	public RemoteProcess resolve (Name n) throws NotFoundException, InvalidNameException;
	public void unbind(Name n) throws NotFoundException, InvalidNameException;

}