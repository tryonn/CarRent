package br.ufpe.cin.middleware.transport;



public interface Transport {

	/*
	  verificar quais desses métodos devem ficar aqui nesta interface; quais as primitivas para todos os modelos de
	  transporte??

	HTTP
	public abstract void open() throws IOException;
	public abstract void send(Object msg) throws IOException;
	public abstract Object receive() throws IOException, ClassNotFoundException;
	public abstract void close();	


	UDP
	public abstract void open(String destinationIP, int destinationPort) throws SocketException, InvalidIPException, InvalidPortException;
	public abstract void send(Object msg, String destinationIP, int destinationPort) throws IOException;
	public abstract Message receive() throws IOException, ClassNotFoundException;	
	public abstract void close();


	TCP
	public abstract void listen() throws IOException, ClassNotFoundException;
	public abstract void send(Object obj) throws IOException, NotConnectedException;
	public abstract void sendTo(Object obj, String destinationIP, int destinationPort ) throws IOException, NotConnectedException, InvalidIPException, NotExistingClientException;
	public abstract void sendToAll(Object obj) throws IOException, NotConnectedException;
	public abstract Message receive() throws IOException, ClassNotFoundException, NotConnectedException;
	public abstract void close(String destination, int destinationPort) throws Throwable;
	
	 */
}
