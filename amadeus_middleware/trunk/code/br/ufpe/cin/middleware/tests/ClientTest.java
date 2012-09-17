package br.ufpe.cin.middleware.tests;

import java.io.IOException;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.transport.tcp.TCP;
import br.ufpe.cin.middleware.util.Debug;

public class ClientTest {

	public static void main(String[] args) {
		AmadeusMiddleware mid = new AmadeusMiddleware("localhost",1099,new Integer(5389));
		mid.open();
		boolean acabou = false;
		while(!acabou) {
			System.out.println("Cliente rodando...");
			System.out.println("Digite a mensagem");
			byte[] array = new byte[30];
			try {
				System.in.read(array);
			} catch (IOException e) {
				Debug.printStack(e);
			}
			String a = new String(array);
			a = a.trim();
			mid.send(a,null,-1);
			Message mensagem = null;
			if (a.equals("teste")) {
				try {
					mensagem = (Message) mid.receive();
					System.out.println("Mensagem recebida: " + mensagem.getContent().toString());
				} catch (Exception e) {
					Debug.printStack(e);;
				} 
				
				acabou = true;
				//mid.close(null, -1);
			}
			
			if (a.equals("remote")) {
//				RemoteCall rc1 = new RemoteCall("metodoX",ClientTest.class.getCanonicalName());
//				RemoteCall rc2 = new RemoteCall("metodoSoma",ClientTest.class.getCanonicalName());
//				RemoteCall rc3 = new RemoteCall("metodoPalindromo",ClientTest.class.getCanonicalName());
//				rc2.args = new Object [] {5,7};
//				rc2.sigs = new Class[] {int.class,int.class};
//				rc3.args = new Object[] {"Metal is the law!!"};
//				rc3.sigs = new Class[] {String.class};
/*				RemoteCallReply rcr = mid.invoke(rc3);
				System.out.println(rcr.content);*/
			}
		}
	}

	public void metodoX() {
		System.out.println("Metodo X executado");
	}
	
	public void metodoSoma(int a, int b) {
		System.out.println("Soma dos números é: " + (a+b));
	}
	
	public void metodoPalindromo(String str) {
		char array [] = new char [str.length()];
		for (int i = 0; i < array.length; i++) {
			array[i] = str.charAt(str.length() - 1 - i);
		}
		//return new String(array);
	}
	
}


class ClientThread extends Thread {
	
	TCP client = null;
	
	public ClientThread(TCP c) {
		this.client = c;
	}
	
	public void run() {
		while(true) {
			try {
				Message m = (Message) client.receive();
				System.out.println(m.getContent().toString());
			} catch (IOException e) {
				Debug.printStack(e);
			} catch (ClassNotFoundException e) {
				Debug.printStack(e);
			} catch (NotConnectedException e) {
				Debug.printStack(e);
			}
		}
	}
}
