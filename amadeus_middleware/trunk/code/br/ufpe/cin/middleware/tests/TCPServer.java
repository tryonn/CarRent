package br.ufpe.cin.middleware.tests;

import java.io.IOException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.transport.tcp.TCPAddress;
import br.ufpe.cin.middleware.transport.tcp.TCPMBean;
import br.ufpe.cin.middleware.util.Debug;

public class TCPServer {

	public static void main(String[] args) 
	{
		AmadeusMiddleware middleware = new AmadeusMiddleware();
		middleware.open();
		TCPMBean servidor;
		try 
		{
			servidor = new TCPAddress(false).createTransport();
			System.err.println("Servidor no ar");
			ServerListen server = new ServerListen(servidor);
			server.start();
			
			while(true) 
			{
				/*	Message mensagem;
				do 
				{
					mensagem = (Message) servidor.receive();
					servidor.sendTo("Resposta do servidor", "172.17.66.37", 2324);
				} while(mensagem == null);
					System.out.println("Mensagem recebida: " + mensagem.getContent());*/
			}
		} catch (IOException e) {
			Debug.printStack(e);
		} catch (InvalidPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
class ServerListen extends Thread 
{
	TCPMBean servidor = null;
	
	public ServerListen(TCPMBean servidor) 
	{
		super();
		this.servidor = servidor;
	}
	
	public void run() 
	{
		/*try 
		{
			while(true) 
			{
				servidor.listen();
			}
		} catch (IOException e) {
			Debug.printStack(e);
		} catch (ClassNotFoundException e) {
			Debug.printStack(e);
		}*/
	}
}


