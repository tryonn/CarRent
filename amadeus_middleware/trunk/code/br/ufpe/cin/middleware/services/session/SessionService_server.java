package br.ufpe.cin.middleware.services.session;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.services.naming.Naming;
import br.ufpe.cin.middleware.services.naming.RemoteProcess;
import br.ufpe.cin.middleware.services.naming.exceptions.AlreadyBoundException;
import br.ufpe.cin.middleware.services.naming.exceptions.NamingServiceException;


public class SessionService_server {
	public static void main(String[] args) {
		try {
			Naming.bind("SessionService", new RemoteProcess("localhost",1511, new SessionService_impl()));
			System.out.println("Servidor de sessão iniciado!!!");
		} catch (AlreadyBoundException e) {
			try {
				Naming.rebind("SessionService", new RemoteProcess("localhost",1511, new SessionService_impl()));
			} catch (NamingServiceException e1) {
				e1.printStackTrace();
			} catch (NotConnectedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (NamingServiceException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
}
