package br.ufpe.cin.middleware.transport.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.transport.Address;

public class TCPAddress implements Address {

	private static final int SERVER_INITIAL_PORT = 1512;
	
	//caso seja instanciado como cliente
	private String host;
	private int remotePort;
	private int localPort;
	
	//caso seja instanciado como servidor
	private boolean server = false;	
	private int serverPort = SERVER_INITIAL_PORT;

	//caso seja instanciado como transferencia de arquivo
	private boolean file = false;
	
	//Construtor usado do lado do cliente
	public TCPAddress(String ip, int serverPort, int clientPort, boolean forFileTransfer) {
		this.host = ip;
		this.remotePort = serverPort;
		this.localPort = clientPort;
		this.server = false;
		this.file = forFileTransfer;
	}
	
	public Socket checkFreeLocalPort(String host, int remotePort, int initialPort, int limit) {
			int retorno = initialPort;
			Socket sock = null;
			for (int i = 0; i < limit; i++) {
				try {
					sock = new Socket(host, remotePort, InetAddress.getLocalHost(), retorno++);
				} catch (UnknownHostException e) {
					e.printStackTrace();
					continue;
				} catch (IOException e) {
					System.err.println("	na porta " + retorno + " não deu! : " + e.getClass());
					continue;
				}
				return sock;
			}			
			throw new RuntimeException("Não foi possível encontrar uma porta livre a partir da porta " + initialPort);
	}

	//Construtor usado no lado servidor
	public TCPAddress(boolean forFileTransfer) {
		this.server = true;
		this.file = forFileTransfer;
	}
	
	public TCP createTransport() throws UnknownHostException, IOException, InvalidPortException, InvalidIPException {
		TCP retorno = null;
		
		if (file)
			retorno = new TCPFile(this);
		else
			retorno = new TCP(this);
		
		if (server) {
			ServerSocket ss = this.checkFreePort(5);
			retorno.setServer(ss);
			retorno.setConnected(true);
		}
		
		return retorno;
	}
	
	private ServerSocket checkFreePort(int limit) {
		this.serverPort = SERVER_INITIAL_PORT;
		while (serverPort <= (SERVER_INITIAL_PORT + limit)) {
			try {
				ServerSocket ss = new ServerSocket(serverPort);
				return ss;
			} catch (Exception e) {
				this.serverPort++;
			}
		}
		throw new RuntimeException(
				String.format("Não foi possível estabelecer conexão entre as portas %d e %d!",
						SERVER_INITIAL_PORT, serverPort));
	}

	
	/*           GETS E SETS           */
	
	public boolean isServer() {
		return server;
	}
	
	public String getHost() {
		return host;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public int getLocalPort() {
		return localPort;
	}
	
	public int getServerPort() {
		return serverPort;
	}
	
}
