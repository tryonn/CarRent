package br.ufpe.cin.middleware.tests;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.util.Debug;

public class ServerTest extends Thread {

	private AmadeusMiddleware middleware;
	
	public ServerTest() {
		middleware = new AmadeusMiddleware();
		middleware.open();
		this.start();
	}
	
	public void run() {
		while(true) {
			middleware.listen();
		}
	}
	
	public Message receive() throws InstanceNotFoundException, MalformedObjectNameException, MBeanException, ReflectionException, NullPointerException, NotConnectedException {
		return this.middleware.receive();
	}
	
	public static void main(String[] args) {
		ServerTest s = new ServerTest();
		System.out.println("Servidor no ar");
		while(true) {
			Message m;
			try {
				m = s.receive();
				System.out.println(m.getContent());
				if(m.getContent().equals("teste")) {
					s.middleware.sendTo("testando", m.getSourceIP(), new Integer(5389));
					s.middleware.close(m.getSourceIP(), m.getSourcePort());
					System.out.println("passou " + s.getState());
				}
			} catch (InstanceNotFoundException e) {
				Debug.printStack(e);
			} catch (MalformedObjectNameException e) {
				Debug.printStack(e);
			} catch (MBeanException e) {
				Debug.printStack(e);
			} catch (ReflectionException e) {
				Debug.printStack(e);
			} catch (NullPointerException e) {
				Debug.printStack(e);
			} catch (NotConnectedException e) {
				Debug.printStack(e);
			}
		}

	}
	
}


