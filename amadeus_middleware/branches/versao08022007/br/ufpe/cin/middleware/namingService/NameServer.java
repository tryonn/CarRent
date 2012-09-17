package br.ufpe.cin.middleware.namingService;

import java.util.Calendar;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.namingService.exceptions.AlreadyBoundException;
import br.ufpe.cin.middleware.namingService.exceptions.InvalidNameException;
import br.ufpe.cin.middleware.namingService.exceptions.NamingServiceException;
import br.ufpe.cin.middleware.namingService.exceptions.NotFoundException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.objects.ServiceMessage;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;

/**
 * Classe que representa o serviço de nomes.
 * 
 * @author Bruno Barros (blbs@cin.ufpe.br)
 * @author Rebeka Gomes (rgo2@cin.ufpe.br)
 *
 */
public class NameServer extends Thread {
	
	private NamingContext table;
	private AmadeusMiddleware middleware;
	
	public NameServer() {
		this.table = new NamingContextImpl();
		this.middleware = new AmadeusMiddleware();
		this.middleware.open();
	}
	
	private void bind(Name n, RemoteProcess obj) throws NotFoundException, InvalidNameException, AlreadyBoundException {
		table.bind(n, obj);
	}


	private void rebind(Name n, RemoteProcess obj) throws NotFoundException, InvalidNameException {
		table.rebind(n, obj);
	}


	private RemoteProcess resolve(Name n) throws NotFoundException, InvalidNameException {
		return table.resolve(n);
	}

	private void unbind(Name n) throws NotFoundException, InvalidNameException {
		table.unbind(n);
	}

	public void run() {
		while(true) {
			try {
				this.middleware.listen();
				this.receive();
			} catch (Exception e) {
				System.err.println(String.format("%19s%s", "",e.toString()));
			} 
		}
	}
	
	private void receive() {
		new Thread(){
			public void run() {
				while(true){
					try {
						startServer();
					} catch (NotConnectedException e) {
						System.err.println(String.format("%19s%s", "",e.toString()));
						this.interrupt();
						break;
					}
				}
			};
		}.start();

	}
	
	public void startServer() throws NotConnectedException {
		Message m = this.middleware.receive();
		ServiceMessage request = (ServiceMessage)m.getContent();
		Object[] args = request.getParameters();
		ServiceMessage response = request;
		try {
			if (request.getOperation() == NamingServiceOperations.BIND){
				this.bind(((Name) args[0]), (RemoteProcess) args[1]);
				response.setReturnedArg(true);
				
			} else if(request.getOperation() == NamingServiceOperations.RESOLVE){
				RemoteProcess foundProcess = this.resolve((Name) args[0]); 
				response.setReturnedArg(foundProcess);
				
			} else if (request.getOperation() == NamingServiceOperations.REBIND){
				this.rebind(((Name) args[0]), (RemoteProcess) args[1]);
				response.setReturnedArg(true);
				
			} else if(request.getOperation() == NamingServiceOperations.UNBIND){
				this.unbind((Name) args[0]);
				response.setReturnedArg(true);
			}
		} catch (NamingServiceException e) {
			System.err.println(String.format("%10s:%5d - %s", m.getSourceIP(), m.getSourcePort(), e.toString()));
			response.setOperation(NamingServiceOperations.EXCEPTION);
			response.setReturnedArg(e);
		} 
		this.middleware.sendTo(response, m.getSourceIP(), m.getSourcePort());
		
	}

	public static void main(String[] args) {
		NameServer ns = new NameServer();
		System.out.println("===== NameServer is Running Since " + Calendar.getInstance().getTime() + " ====");
		ns.start();
	}
	
}


