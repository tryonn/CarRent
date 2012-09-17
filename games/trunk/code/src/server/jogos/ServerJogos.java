package server.jogos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import server.Constantes;
import server.Protocol;

public class ServerJogos extends Thread{

	public void run(){
		ServerSocket server = null;
		HandlerLoginGame handlerLogingame = HandlerLoginGame.getInstance(); 
		
		try {
			server = new ServerSocket(Constantes.DEFAULT_PORT_GAME);
			System.out.println("Esperando os JOGOS se conectarem...");
			
			
			while(true){
				Socket socket = null;
				try {
					socket = server.accept();
					Protocol protocol = new Protocol(socket);

					handlerLogingame.addProtocol(protocol);
					System.out.println("Conexao de Jogo de ip = "+socket.getInetAddress().getHostAddress()+" conectou as "+ new Date());					
					
					
				} catch (IOException e) {
					System.out.println("Ocorreu um erro ao criar o socket para o jogo");
					e.printStackTrace();
				}				
			}
		} catch (IOException e) {
			System.out.println("Erro ao criar o ServerSocket");
			e.printStackTrace();
		}
	}
		
}
