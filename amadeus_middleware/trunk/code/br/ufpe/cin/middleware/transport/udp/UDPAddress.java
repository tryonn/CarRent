package br.ufpe.cin.middleware.transport.udp;

import java.net.SocketException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.transport.Address;

public class UDPAddress implements Address {

	public static final int SERVER_PORT = 1099;

	private boolean server = false;	
	
	private int port;
	private String destinationIp;
	private int destinationPort;
	
	//Construtor usado do lado do cliente
	public UDPAddress(String destinationIp) throws InvalidIPException {
		this.server = false;
		this.setDestinationIP(destinationIp);
	}
	
	//Construtor usado no lado servidor
	public UDPAddress() {
		this.server = true;
		this.port = SERVER_PORT;
	}
	
	public UDP createTransport() throws SocketException, InvalidPortException, InvalidIPException {
		UDP retorno =  new UDP(this);
		return retorno;
	}
	
	private boolean isValidPort(int port) {
		if ((port > 0 && port < 1023) || (port > 65535)) {
			return false;
		} else {
			return true;
		}
	}

	
	/*           GETS E SETS           */
	
	
	public boolean isServer() {
		return server;
	}

	public void setDestinationIP(String destinationIP) throws InvalidIPException {
		if (destinationIP != null) {
			this.destinationIp = destinationIP;
		} else {
			throw new InvalidIPException();
		}
	}

	public void setDestinationPort(int destinationPort) throws InvalidPortException {
		if (isValidPort(destinationPort)) {
			this.destinationPort = destinationPort;
		} else {
			throw new InvalidPortException();
		}
	}

	public int getDestinationPort() {
		return destinationPort;
	}

	public String getDestinationIP() {
		return destinationIp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int localPort) throws InvalidPortException {
		if (isValidPort(localPort)) {
			this.port = localPort;
		} else {
			throw new InvalidPortException();
		}
		
	}
		
}
