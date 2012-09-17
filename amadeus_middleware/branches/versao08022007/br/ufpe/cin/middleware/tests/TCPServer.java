package br.ufpe.cin.middleware.tests;

import java.io.IOException;

import br.ufpe.cin.middleware.communication.tcp.TCP;
import br.ufpe.cin.middleware.communication.tcp.TCPMBean;
import br.ufpe.cin.middleware.exceptions.NullSocketException;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.util.Debug;

public class TCPServer {

	public static void main(String[] args) 
	{
		AmadeusMiddleware middleware = new AmadeusMiddleware();
		middleware.open();
		TCPMBean servidor;
		try 
		{
			servidor = new TCP();
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
		} catch (NullSocketException e) {
			Debug.printStack(e);
		/*} catch (ClassNotFoundException e) {
			Debug.printStack(e);
		} catch (NotConnectedException e) {
			Debug.printStack(e);
		} catch (InvalidIPException e) {
			Debug.printStack(e);
		} catch (NotExistingClientException e) {
			Debug.printStack(e);*/
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


