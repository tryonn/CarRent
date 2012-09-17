package br.ufpe.cin.middleware.services.naming;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.objects.ServiceMessage;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.proxies.Skeleton_abstract;
import br.ufpe.cin.middleware.proxies.Stub_abstract;
import br.ufpe.cin.middleware.services.calculator.Calculator_impl;
import br.ufpe.cin.middleware.services.calculator.Calculator_skeleton;
import br.ufpe.cin.middleware.services.calculator.Calculator_stub;
import br.ufpe.cin.middleware.services.naming.exceptions.NamingServiceException;
import br.ufpe.cin.middleware.services.security.DBWrapper_impl;
import br.ufpe.cin.middleware.services.security.DBWrapper_skeleton;
import br.ufpe.cin.middleware.services.security.DBWrapper_stub;
import br.ufpe.cin.middleware.services.session.SessionService_impl;
import br.ufpe.cin.middleware.services.session.SessionService_skeleton;
import br.ufpe.cin.middleware.services.session.SessionService_stub;
import br.ufpe.cin.middleware.util.XMLParser;

public class Naming {
	
	public static String HOST = XMLParser.hosts.get("namingService");
	public static int PORT = XMLParser.localPorts.get("namingService");
	public static int SERVER_PORT = XMLParser.serverPorts.get("namingService");
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
		Skeleton_abstract skeleton = null;
		if(obj.remoteInterface instanceof Calculator_impl){
			new Calculator_skeleton();
		} else if(obj.remoteInterface instanceof DBWrapper_impl){
			//	TODO Verificar isso apos a apresentação
			skeleton = new DBWrapper_skeleton();
			skeleton.startServices();
		}else if(obj.remoteInterface instanceof SessionService_impl){
			skeleton = new SessionService_skeleton();
			skeleton.startServices();
		}
	
	}

	public static void rebind (String n, RemoteProcess obj) throws NamingServiceException, NotConnectedException {
		process(n,NamingServiceOperations.REBIND,obj);
	}
	
	public static RemoteInterface resolve (String n) throws NamingServiceException, NotConnectedException {
		//TODO if para ver qual stub criar!!!
		Stub_abstract stub = null;
		ServiceMessage sm = process(n,NamingServiceOperations.RESOLVE, null);
		RemoteProcess rp = (RemoteProcess) sm.getReturnedArg(); 
		if(rp.remoteInterface instanceof Calculator_impl){
			stub = new Calculator_stub(rp.host, rp.port);
		} else if(rp.remoteInterface instanceof DBWrapper_impl){
			stub = new DBWrapper_stub(rp.host, rp.port);
		} else if(rp.remoteInterface instanceof SessionService_impl){
			stub = new SessionService_stub(rp.host, rp.port);
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
