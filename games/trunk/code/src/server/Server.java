package server; 

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import server.jogos.ServerJogos;
import server.lobby.HandlerLogin;


public class Server {

	private static ServerSocket server;

	public static void main(String args[]){
		setServer(null);
		HandlerLogin handlerLogin = HandlerLogin.getInstance(); 
		
		try {
			setServer(new ServerSocket(Constantes.DEFAULT_PORT));
			System.out.println("Esperando os clientes se conectarem...");
			
			Thread t = new ServerJogos();
			t.start();
			
			while(true){
				Socket socket = null;
				try {
					socket = getServer().accept();
					Protocol protocol = new Protocol(socket);

					handlerLogin.addProtocol(protocol);
					System.out.println("Cliente de ip = "+socket.getInetAddress().getHostAddress()+" conectou as "+ new Date());					

				} catch (IOException e) {
					System.out.println("Ocorreu um erro ao criar o socket para o cliente, ou ao criar os buffers");
					e.printStackTrace();
				}				
			}
		} catch (IOException e) {
			System.out.println("Erro ao criar o ServerSocket");
			e.printStackTrace();
		}
	}
		
	static void setServer(ServerSocket server) {
		Server.server = server;
	}

	static ServerSocket getServer() {
		return server;
	}

}
