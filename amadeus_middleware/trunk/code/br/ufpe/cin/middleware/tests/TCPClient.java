package br.ufpe.cin.middleware.tests;

import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.transport.tcp.TCP;
import br.ufpe.cin.middleware.transport.tcp.TCPAddress;
import br.ufpe.cin.middleware.util.Debug;

public class TCPClient {

	public static void main(String[] args) {
		try {
			TCP client = new TCPAddress("localhost",1099,2324,false).createTransport();
			byte array[] = new byte[500];
			String enviada = null;
			Message recebida = null;
			
			do
			{
				System.out.println("Digite uma mensagem: ");
				System.in.read(array);
				enviada = new String(array);
				enviada = enviada.trim();
				client.send(enviada);
				recebida = client.receive();
				System.out.println("Mensagem recebida: " + recebida.getContent().toString());
			} while (!enviada.equals("Fim"));
			
			client.close(null,0);			
			
		} catch (Throwable e) {
			Debug.printStack(e);
		}
	}
}
