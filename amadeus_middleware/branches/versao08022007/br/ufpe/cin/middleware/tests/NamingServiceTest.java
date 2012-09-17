package br.ufpe.cin.middleware.tests;

import br.ufpe.cin.middleware.namingService.Naming;
import br.ufpe.cin.middleware.namingService.RemoteInterface;
import br.ufpe.cin.middleware.namingService.RemoteProcess;
import br.ufpe.cin.middleware.util.Debug;

public class NamingServiceTest {

	public static void main(String[] args) {
		try {
			//Naming.unbind("metal");
			//System.out.println( Naming.resolve("metal"));
			Naming.bind("metal", new RemoteProcess("172.17.15.20",1502,new Calculadora2()));
			while(true) {
			 Naming.rebind("metal", new RemoteProcess("localhost",1099,new Calculadora()));
			}
		} catch (Exception e) {
			Debug.printStack(e);
		} 
	}
}

class Calculadora implements RemoteInterface{


	private static final long serialVersionUID = 1L;

	int soma(int a, int b){
		return a+b;
	}
}

class Calculadora2 implements RemoteInterface{


	private static final long serialVersionUID = 1L;

	int soma(int a, int b){
		return a+b;
	}
}

