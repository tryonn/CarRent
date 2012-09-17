package br.ufpe.cin.middleware.namingService;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.namingService.exceptions.NamingServiceException;
import br.ufpe.cin.middleware.objects.ServiceMessage;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.stub_skeleton.proxies.Calculator_impl;
import br.ufpe.cin.middleware.stub_skeleton.proxies.Calculator_skeleton;
import br.ufpe.cin.middleware.stub_skeleton.proxies.Calculator_stub;
import br.ufpe.cin.middleware.util.XMLParser;

public class Naming {
	
	public static String HOST = XMLParser.hosts.get("Calculator");
	public static int PORT = XMLParser.localPorts.get("Calculator");
	public static int SERVER_PORT = XMLParser.serverPorts.get("Calculator");
	private static AmadeusMiddleware mid = null;
	
	private static void start() {
		if (mid == null){
			mid = new AmadeusMiddleware(HOST,SERVER_PORT,PORT);
			mid.open();
		}
	}

	public static void bind (String n, RemoteProcess obj) throws NamingServiceException, NotConnectedException {
		//TODO tem que ter um if para ver que interface é!!!
		process(n, NamingServiceOperations.BIND, obj);
		if(obj.remoteInterface instanceof Calculator_impl){
			new Calculator_skeleton();
		}
	}

	public static void rebind (String n, RemoteProcess obj) throws NamingServiceException, NotConnectedException {
		process(n,NamingServiceOperations.REBIND,obj);
	}
	
	public static RemoteInterface resolve (String n) throws NamingServiceException, NotConnectedException {
		//TODO if para ver qual stub criar!!!
		Calculator_stub stub = null;
		ServiceMessage sm = process(n,NamingServiceOperations.RESOLVE, null);
		RemoteProcess rp = (RemoteProcess) sm.getReturnedArg(); 
		if(rp.remoteInterface instanceof Calculator_impl){
			stub = new Calculator_stub();
		}
		
		return stub;
	}
	
	public static void unbind(String n) throws NamingServiceException, NotConnectedException {
		process(n, NamingServiceOperations.UNBIND, null);
	}

	private static ServiceMessage process(String n, NamingServiceOperations op, RemoteProcess obj) throws NotConnectedException, NamingServiceException {
		start();
		Name n2 = new Name();
		n2.addComponent(new NameComponent(n,"Amadeus"));
		ServiceMessage sm = null;
		if (obj != null) {
			sm = new ServiceMessage(op,n2,obj);
		} else {
			sm = new ServiceMessage(op,n2);
		}
		mid.send(sm, HOST, PORT);
		sm = (ServiceMessage) mid.receive().getContent();
		if (sm.getOperation() == NamingServiceOperations.EXCEPTION) {
			throw (NamingServiceException) sm.getReturnedArg();
		}
		return sm;
	}


}
