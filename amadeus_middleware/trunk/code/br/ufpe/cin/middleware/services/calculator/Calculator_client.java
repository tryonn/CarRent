package br.ufpe.cin.middleware.services.calculator;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Locale;

import br.ufpe.cin.middleware.services.naming.Naming;
import br.ufpe.cin.middleware.util.Debug;

public class Calculator_client {

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		System.out.println("Cliente inicializado!!!");
		Calculator calc = null;
		try {
			 calc = (Calculator) Naming.resolve("Calculator");
		} catch (Exception e) {
			Debug.printStack(e);
		} 
		System.out.println();
		for (int i = 0; i < 10; i++) {
			int a, b;
			a = (int) (Math.random() * 100);
			b = (int) (Math.random() * 100);
			try { Thread.sleep(2000); } catch(Throwable e) { }
			System.out.println(String.format(new Locale("pt"),"%3d + %3d = %3d",a,b,calc.add(a,b)));
		}
	}
}
