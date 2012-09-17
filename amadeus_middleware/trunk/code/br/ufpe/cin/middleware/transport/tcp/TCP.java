package br.ufpe.cin.middleware.transport.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.exceptions.NotExistingClientException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.objects.controlMessages.TCPControlMessage;
import br.ufpe.cin.middleware.transport.Buffer;
import br.ufpe.cin.middleware.transport.Transport;
import br.ufpe.cin.middleware.util.Port;

public class TCP implements Transport, TCPMBean {

	private TCPAddress address;
	private boolean connected = false;

	private Socket socket;
	private ServerSocket server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Buffer<Message> messages;
	private Buffer<TCPHandler> connections;

	//portas
	private Port inPort = new Port(this.getClass().getSimpleName() + "_inPort");
	private Port outPort = new Port(this.getClass().getSimpleName() + "_outPort");
	private boolean ready = false;


	public TCP(TCPAddress address) throws UnknownHostException, IOException, InvalidPortException, InvalidIPException {
		this.address = address;
		if (this.address.isServer()) {
			this.connections = new Buffer<TCPHandler>();
			this.messages = new Buffer<Message>();
		} else {
			this.open(address.getHost(), address.getRemotePort(), address.getLocalPort());
		}
	}
	
	
	public void listen() throws IOException, ClassNotFoundException 
	{
		Socket socketClient = this.server.accept();
		TCPHandler clientConnection = new TCPHandler(socketClient, this);
		this.connections.write(clientConnection);
	}

	//exclusivo do cliente
	public void send(Object msg) throws IOException, NotConnectedException 
	{
		if(isConnected()) 
		{
			String sourceIP = socket.getLocalAddress().getHostAddress();
			int sourcePort = socket.getLocalPort();
			Message message = new Message("TCP",msg,"Server",sourceIP,this.address.getServerPort(),sourcePort);
			out.writeObject(message);
			out.flush();
		} 
		else 
		{
			throw new NotConnectedException();
		}

	}

	//ambos
	public void sendTo(Object msg, String destinationIP, int destinationPort) 
	throws IOException, NotConnectedException, InvalidIPException, NotExistingClientException 
	{
		if (this.address.isServer()) 
			this.serverSendTo(msg, destinationIP, destinationPort);
		else 
			this.clientSendTo(msg, destinationIP, destinationPort);				
	}
	
	//ambos
	public void sendToAll(Object msg) throws IOException, NotConnectedException 
	{
		if (this.address.isServer()) 
			this.serverSendToAll(msg);
		else 
			this.clientSendToAll(msg);		
	}

	public Message receive() throws IOException, ClassNotFoundException, NotConnectedException 
	{
		Message ret = null;
		if (this.address.isServer()) 
			ret = this.serverReceive();
		else 
			ret = this.clientReceive();

		return ret;
	}	

	public void close(String destination, int destinationPort) throws Throwable 
	{
		if (this.address.isServer()) 
			this.serverClose(destination, destinationPort);
		else 
			this.clientClose();				
	}

	private void open(String ip, int port, int localPort) 
	throws UnknownHostException, IOException, InvalidPortException, InvalidIPException 
	{
		if(!isConnected()) 
		{
			if(ip != null && !ip.trim().equals("")) 
			{
				if (!((port > 0 && port < (1 << 10)) || (port > (1 << 20)))) 
				{
					socket = this.address.checkFreeLocalPort(ip,port,localPort, 10);
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					this.setIn(in);
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					this.setOut(out);
					this.setConnected(true);
				} 
				else 
				{
					throw new InvalidPortException();
				}
			} 
			else 
			{
				throw new InvalidIPException();
			}
		}		
	}
	
	public void writeInPort(Object obj, String method) throws Throwable {
		this.inPort.write(method);
		this.inPort.write(obj);
		this.process();
	}

	public void writeInPort(Object obj1, Object obj2, Object obj3, String method) throws Throwable {
		this.inPort.write(method);
		this.inPort.write(obj1);
		this.inPort.write(obj2);
		this.inPort.write(obj3);
		this.process();
	}

	public Object readOutPort() throws IOException, ClassNotFoundException{
		while(!this.ready){}
		this.ready = false;
		Object returnObj = this.outPort.read(0); 
		this.outPort.clear();
		return returnObj;
	}
	
	private void process() throws Throwable{
		String method;
		try {
			method = (String) this.inPort.read(0);
			if (method.equals("open")){
				this.open((String)this.inPort.read(1),(Integer)this.inPort.read(2),(Integer)this.inPort.read(3));
			}
			else if(method.equals("listen")){
				this.listen();
			}else if(method.equals("send")){
				this.send(this.inPort.read(1));
			}else if(method.equals("sendTo")){
				this.sendTo(this.inPort.read(1), (String)this.inPort.read(2), (Integer)this.inPort.read(3));
			}else if(method.equals("sendToAll")){
				this.sendToAll(this.inPort.read(1));
			}else if(method.equals("receive")){
				Message msg = null;
				msg = this.receive();
				this.outPort.write(msg);
			}else if(method.equals("close")){
				this.close((String)this.inPort.read(1),(Integer)this.inPort.read(2));
			}
			this.inPort.clear();

		} finally {
			this.ready = true;
		}
	}

	private void clientSendTo(Object msg, String destinationIP, int destinationPort) 
	throws IOException, InvalidIPException, NotConnectedException 
	{
		if(isConnected()) 
		{
			if(destinationIP != null) 
			{
				String source = socket.getLocalAddress().getHostAddress();
				Message message = new Message("TCP",msg,destinationIP,source,destinationPort,this.getSocket().getLocalPort());
				out.writeObject(message);
				out.flush();
			} 
			else 
			{
				throw new InvalidIPException();
			}
		} 
		else
		{
			throw new NotConnectedException();
		}

	}

	private void clientSendToAll(Object msg) throws IOException, NotConnectedException 
	{
		if(isConnected()) 
		{
			String source = socket.getLocalAddress().getHostAddress();
			Message message = new Message("TCP",msg,"ToAll",source,-1,this.getSocket().getLocalPort());
			out.writeObject(message);
			out.flush();
		} 
		else 
		{
			throw new NotConnectedException();
		}
	}

	private Message clientReceive() throws IOException, ClassNotFoundException, NotConnectedException 
	{
		Message ret = null;
		if(isConnected()) 
		{			
			ret = (Message)in.readObject();
			ret.checkMessage();
		} 
		else 
		{
			throw new NotConnectedException();
		}
		return ret;
	}

	private void clientClose() throws IOException, NotConnectedException 
	{
		if(!socket.isClosed()) 
		{
			if (socket != null) 
			{
				String source = socket.getLocalAddress().getHostAddress();
				TCPControlMessage closeMessage = new TCPControlMessage(TCPControlMessage.CLOSE_CONNECTION,"Server",source);
				out.writeObject(closeMessage);
				out.flush();
				this.out.close();
				this.in.close();
				this.socket.close();
				this.setConnected(false);
			} 
			else
			{
				throw new SocketException("Socket nulo");
			}
		} 
		else
		{
			throw new NotConnectedException();
		}

	}

	private void serverSendTo(Object msg, String destinationIP, int destinationPort) 
	throws IOException, NotExistingClientException 
	{
		boolean found = false;
		Iterator<TCPHandler> it = connections.iterator();
		TCPHandler client = null;
		String destinationClient = null;
		int port = 0;

		while (it.hasNext() && !found) 
		{
			client = it.next();
			destinationClient = client.getSocket().getInetAddress().getHostAddress();
			port = client.getSocket().getPort();
			if (destinationClient.equals(destinationIP) && destinationPort == port) 
				found = true;			
		}
		if(found) 
			client.send(msg);
		else 
			throw new NotExistingClientException();		
	}

	private void serverSendToAll(Object msg) throws IOException 
	{
		Iterator<TCPHandler> ite = connections.iterator();
		while(ite.hasNext()) 
		{
			TCPHandler client = ite.next();
			client.send(msg);
		}		
	}

	private synchronized Message serverReceive() throws IOException, ClassNotFoundException 
	{
		Message msg = null;
		while((msg = this.messages.read()) == null); //Espera receber uma mensagem
		msg.checkMessage();
		return msg;
	}

	private void serverClose(String destination, int destinationPort) throws Throwable 
	{
		boolean found = false;
		Iterator<TCPHandler> it = connections.iterator();
		TCPHandler client = null;
		String destinationClient = null;
		int port = 0;

		while (it.hasNext() && !found) 
		{
			client = it.next();
			destinationClient = client.getSocket().getInetAddress().getHostAddress();
			port = client.getSocket().getPort();
			if (destinationClient.equals(destination) && destinationPort == port) {
				found = true;
			}
		}
		if(found) {
			client.closeConnection();
			connections.remove(client);
		}
	}


	
	
	
	
	/*           GETS E SETS           */

	
	
	
	
	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public Buffer<TCPHandler> getConnections() {
		return connections;
	}
	
	public void setConnections(Buffer<TCPHandler> connections) {
		this.connections = connections;
	}
	
	public Buffer<Message> getMessages() {
		return messages;
	}
	
	public void setMessages(Buffer<Message> messages) {
		this.messages = messages;
	}
	
	public ObjectInputStream getIn() {
		return in;
	}
	
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	
	public ObjectOutputStream getOut() {
		return out;
	}
	
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
	
	public ServerSocket getServer() {
		return server;
	}
	
	public void setServer(ServerSocket server) {
		this.server = server;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
