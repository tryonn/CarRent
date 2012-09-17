package br.ufpe.cin.middleware.stub_skeleton;

import java.io.IOException;

import br.ufpe.cin.middleware.namingService.Naming;
import br.ufpe.cin.middleware.namingService.RemoteProcess;
import br.ufpe.cin.middleware.stub_skeleton.proxies.Calculator_impl;
import br.ufpe.cin.middleware.util.Debug;

public class Server {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Debug.warning(Server.class, "Servidor inicializado!!!");
		try {
			Naming.bind("calculator", new RemoteProcess("localhost",1510,new Calculator_impl()));
		} catch (Exception e) {
			Debug.printStack(e);
		}
		
	}

}

