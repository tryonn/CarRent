package br.ufpe.cin.middleware.tests;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.transport.udp.UDP;
import br.ufpe.cin.middleware.transport.udp.UDPAddress;
import br.ufpe.cin.middleware.util.Debug;

public class UDPServer {

	public static void main(String[] args) {
		try {
			UDP server = new UDPAddress().createTransport();
			System.err.println("Servidor UDP registrado");
			while(true) {
				try {
					System.out.println("Esperando Mensagem de Solicitação");
					Message msg = server.receive();
					String solicitacao = (String) msg.getContent();
					
					System.out.println(solicitacao);
					if(solicitacao.equalsIgnoreCase("Arquivo1")) {
						File f = new File("arquivo1.wmv");
						server.send(f,msg.getSourceIP(),msg.getSourcePort());
					} else if(solicitacao.equalsIgnoreCase("Arquivo2")) {
						File f = new File("arquivo1.wmv");
						server.send(f,msg.getSourceIP(),msg.getSourcePort());
					}
					System.out.println("Mensagem resposta enviada");
				} catch (IOException e) {
					Debug.printStack(e);
				} catch (ClassNotFoundException e) {
					Debug.printStack(e);
				}
				
			}
		} catch (SocketException e) {
			Debug.printStack(e);
		} catch (InvalidPortException e) {
			Debug.printStack(e);
		} catch (InvalidIPException e) {
			e.printStackTrace();
		}
		
	}
}
