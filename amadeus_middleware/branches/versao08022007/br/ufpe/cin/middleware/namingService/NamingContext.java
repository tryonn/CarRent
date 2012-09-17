package br.ufpe.cin.middleware.namingService;

import br.ufpe.cin.middleware.namingService.exceptions.AlreadyBoundException;
import br.ufpe.cin.middleware.namingService.exceptions.InvalidNameException;
import br.ufpe.cin.middleware.namingService.exceptions.NotFoundException;

public interface NamingContext {

	public void bind (Name n, RemoteProcess obj) throws NotFoundException, InvalidNameException, AlreadyBoundException;
	public void rebind (Name n, RemoteProcess obj) throws NotFoundException, InvalidNameException;
	public RemoteProcess resolve (Name n) throws NotFoundException, InvalidNameException;
	public void unbind(Name n) throws NotFoundException, InvalidNameException;

}