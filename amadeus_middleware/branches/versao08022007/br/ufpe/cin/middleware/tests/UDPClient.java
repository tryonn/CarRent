package br.ufpe.cin.middleware.tests;

import java.io.IOException;
import java.net.SocketException;

import br.ufpe.cin.middleware.communication.udp.UDP;
import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.util.Debug;

public class UDPClient {

	public static void main(String[] args) {
		try {
			UDP client = new UDP("localhost");
			System.out.println("Cliente online");
			System.out.println("Enviar mensagem");
			client.send("Arquivo2",null,0);
			System.out.println("Enviou");
			System.out.println("Receber Resposta");
			Message resp = client.receive();
			System.out.println("Recebeu");
			System.out.println(resp.getContent().toString());
			//byte[] temp = (byte[]) resp.getContent();
			//File file = new File("teste.wmv");
			//FileOutputStream output = new FileOutputStream(file);
			//output.write(temp);
			//output.flush();
			//output.close();
			System.out.println("OK");
			//System.out.println(recebido);
			
		} catch (SocketException e) {
			Debug.printStack(e);
		} catch (InvalidPortException e) {
			Debug.printStack(e);
		} catch (InvalidIPException e) {
			Debug.printStack(e);
		} catch (IOException e) {
			Debug.printStack(e);
		} catch (ClassNotFoundException e) {
			Debug.printStack(e);
		}
	}
}
