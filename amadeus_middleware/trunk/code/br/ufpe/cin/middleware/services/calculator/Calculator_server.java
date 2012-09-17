package br.ufpe.cin.middleware.services.calculator;

import java.io.IOException;

import br.ufpe.cin.middleware.services.naming.Naming;
import br.ufpe.cin.middleware.services.naming.RemoteProcess;
import br.ufpe.cin.middleware.util.Debug;

public class Calculator_server {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Debug.warning(Calculator_server.class, "Servidor inicializado!!!");
		try {
			Naming.bind("Calculator", new RemoteProcess("localhost",1510,new Calculator_impl()));
		} catch (Exception e) {
			Debug.printStack(e);
		}
		
	}

}

