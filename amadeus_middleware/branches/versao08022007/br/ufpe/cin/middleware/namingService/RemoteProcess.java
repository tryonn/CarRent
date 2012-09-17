package br.ufpe.cin.middleware.namingService;

import java.io.Serializable;

/**
 * Classe que representa um processo remoto.
 * 
 * @author Bruno Barros (blbs@cin.ufpe.br)
 * @author Rebeka Gomes (rgo2@cin.ufpe.br)
 * 
 */
public class RemoteProcess implements Serializable {
	
	private static final long serialVersionUID = 1L;

	int port;
	String host;
	RemoteInterface remoteInterface;
	
	public RemoteProcess(String host, int port, RemoteInterface remoteInterface) {
		this.host = host;
		this.port = port;
		this.remoteInterface = remoteInterface;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public RemoteInterface getRemoteInterface() {
		return remoteInterface;
	}

	public void setRemoteInterface(RemoteInterface remoteInterface) {
		this.remoteInterface = remoteInterface;
	}
	
}
