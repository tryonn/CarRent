package br.ufpe.cin.middleware.communication.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.exceptions.NotExistingClientException;
import br.ufpe.cin.middleware.exceptions.NullSocketException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.objects.controlMessages.TCPControlMessage;
import br.ufpe.cin.middleware.util.Port;

/**
 * Classe respons�vel por implementar o comportamento da transmiss�o de dados
 * fazendo uso do protocolo TCP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class TCP implements TCPMBean {

	private int idMessage = 0;
	private long idConnection = 0;
	private boolean connected = false;
	private boolean isServer = false;

	protected int SERVER_PORT = 1099;
	protected static final int SERVER_INITIAL_PORT = 1099;
	private static final String PROTOCOL = "TCP";
	private static final String SERVER = "Server";
	private static final String TO_ALL = "ToAll";

	private Socket socket;
	private ServerSocket server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	protected Map<Integer,Message> messages;
	private Map<Long,TCPHandler> connections;

	//portas
	private Port inPort = new Port(this.getClass().getSimpleName() + "_inPort");
	private Port outPort = new Port(this.getClass().getSimpleName() + "_outPort");
	private boolean ready = false;

	/**
	 * Construtor da classe TCP do lado do cliente.
	 * 
	 * @param host endere�o host da conex�o.
	 * @param serverPort n�mero da porta do lado do cliente.
	 * @param bindPort n�mero da porta da conex�o.
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InvalidPortException
	 * @throws InvalidIPException
	 */
	public TCP(String host, int serverPort, int bindPort) 
	throws UnknownHostException, IOException, InvalidPortException, InvalidIPException 
	{
		this.open(host,serverPort,bindPort);
		this.isServer = false;
	}
	/**
	 * Construtor da classe TCP do lado do servidor.
	 * 
	 * @throws IOException
	 * @throws NullSocketException
	 */
	public TCP() throws IOException, NullSocketException 
	{
		checkFreePort(5);
		this.connections = Collections.synchronizedMap(new Hashtable<Long,TCPHandler>());
		this.messages = Collections.synchronizedMap(new Hashtable<Integer,Message>());
		this.isServer = true;
	}

	private void checkFreePort(int limit) {
		SERVER_PORT = SERVER_INITIAL_PORT;
		boolean naoDeuErro = false;
		while (!naoDeuErro && SERVER_PORT <= (SERVER_INITIAL_PORT + limit)) {
			try {
				this.setServer(new ServerSocket(SERVER_PORT));
				naoDeuErro = true;
				return;
			} catch (Exception e) {
				SERVER_PORT++;
			}
		}
		throw new RuntimeException(
				String.format("N�o foi poss�vel estabelecer conex�o entre as portas %d e %d!",
						SERVER_INITIAL_PORT, SERVER_PORT));
	}
	/**
	 * Aguarda por uma conex�o. (m�todo exclusivo do servidor).
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void listen() throws IOException, ClassNotFoundException 
	{
		Socket socketClient = this.server.accept();
		TCPHandler clientConnection = new TCPHandler(socketClient, this);
		connections.put(idConnection, clientConnection);
		idConnection++;
	}
	/**
	 * M�todo que envia um objeto. (m�todo exclusivo do cliente)
	 *   
	 * @param obj objeto a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
	public void send(Object msg) throws IOException, NotConnectedException 
	{
		if(isConnected()) 
		{
			String source = socket.getLocalAddress().getHostAddress();
			int sourcePort = socket.getLocalPort();
			Message message = new Message(PROTOCOL,msg,SERVER,source,SERVER_PORT,sourcePort);
			out.writeObject(message);
			out.flush();
		} 
		else 
		{
			throw new NotConnectedException();
		}

	}
	/**
	 * M�todo que envia um objeto, atrav�s de um host e de uma porta.
	 * (tanto para o cliente quanto para o servidor)
	 *    
	 * @param obj objeto a ser enviado.
	 * @param destinationIP endere�o de IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws IOException
	 * @throws NotConnectedException
	 * @throws InvalidIPException
	 * @throws NotExistingClientException
	 */
	public void sendTo(Object msg, String destinationIP, int destinationPort) 
	throws IOException, NotConnectedException, InvalidIPException, NotExistingClientException 
	{
		if (isServer) 
			this.serverSendTo(msg, destinationIP, destinationPort);
		else 
			this.clientSendTo(msg, destinationIP, destinationPort);				
	}
	/**
	 * M�todo que envia um objeto para todos os clientes.
	 * (m�todo exclusivo do servidor).
	 * 
	 * @param obj objeto a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
	public void sendToAll(Object msg) throws IOException, NotConnectedException 
	{
		if (isServer) 
			this.serverSendToAll(msg);
		else 
			this.clientSendToAll(msg);		
	}
	/**
	 * Recebe uma mensagem.
	 * 
	 * @return Retorna a mensagem recebida.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws NotConnectedException
	 */
	public Message receive() throws IOException, ClassNotFoundException, NotConnectedException 
	{
		Message ret = null;
		if (isServer) 
			ret = this.serverReceive();
		else 
			ret = this.clientReceive();

		return ret;
	}	
	/**
	 * Fecha uma conex�o. 
	 * 
	 * @param destination endere�o IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws Throwable
	 */
	public void close(String destination, int destinationPort) throws Throwable 
	{
		if (isServer) 
			this.serverClose(destination, destinationPort);
		else 
			this.clientClose();				
	}
	/**
	 * Escreve um objeto na porta de entrada deste componente.
	 * 
	 * @param method - M�todo a ser utilizado.
	 * @param obj - Objeto a ser escrito.
	 * @throws Throwable 
	 */
	public void writeInPort(Object obj, String method) throws Throwable {
		this.inPort.write(method);
		this.inPort.write(obj);
		this.process();
	}
	/**
	 * Escreve objetos na porta de entrada deste componente.
	 * 
	 * @param method - M�todo a ser utilizado.
	 * @param obj1 - Objeto 1 a ser escrito.
	 * @param obj2 - Objeto 2 a ser escrito.
	 * @param obj3 - Objeto 3 a ser escrito.
	 * @throws Throwable 
	 */
	public void writeInPort(Object obj1, Object obj2, Object obj3, String method) throws Throwable {
		this.inPort.write(method);
		this.inPort.write(obj1);
		this.inPort.write(obj2);
		this.inPort.write(obj3);
		this.process();
	}
	/**
	 * L� um objeto da porta de sa�da deste componente.
	 * 
	 * @return Retorna o objeto lido da porta de sa�da.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object readOutPort() throws IOException, ClassNotFoundException{
		while(!this.ready){}
		this.ready = false;
		Object returnObj = this.outPort.read(0); 
		this.outPort.clear();
		return returnObj;
	}
	/**
	 * Abre uma nova conex�o com um host.
	 * 
	 * @param ip endere�o host do destino.
	 * @param port porta do destino.
	 * @param localPort porta local.
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InvalidPortException
	 * @throws InvalidIPException
	 */
	private void open(String ip, int port, int localPort) 
	throws UnknownHostException, IOException, InvalidPortException, InvalidIPException 
	{
		//TODO este m�todo REALMENTE eh publico?! alguem + precisa dele??
		if(!isConnected()) 
		{
			if(ip != null && !ip.trim().equals("")) 
			{
				if (isValidPort(port)) 
				{
					socket = new Socket(ip,port,InetAddress.getLocalHost(),localPort);
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					//socket.getInputStream();
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
	/**
	 * Faz o processamento desta classe, tratando portas de sa�da e de entrada.
	 * 
	 * @throws Throwable
	 */
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
	/**
	 * M�todo que envia um objeto, atrav�s de um host e de uma porta. 
	 * (implementa��o do lado do cliente).
	 *    
	 * @param obj objeto a ser enviado.
	 * @param destinationIP endere�o de IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws IOException
	 * @throws NotConnectedException
	 * @throws InvalidIPException
	 */
	private void clientSendTo(Object msg, String destinationIP, int destinationPort) 
	throws IOException, InvalidIPException, NotConnectedException 
	{
		if(isConnected()) 
		{
			if(destinationIP != null) 
			{
				String source = socket.getLocalAddress().getHostAddress();
				Message message = new Message(PROTOCOL,msg,destinationIP,source,destinationPort,this.getSocket().getLocalPort());
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
	/**
	 * M�todo que envia um objeto para todos os clientes conectados.
	 * (implementa��o do lado do cliente).
	 *    
	 * @param obj objeto a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
	private void clientSendToAll(Object msg) throws IOException, NotConnectedException 
	{
		if(isConnected()) 
		{
			String source = socket.getLocalAddress().getHostAddress();
			Message message = new Message(PROTOCOL,msg,TO_ALL,source,-1,this.getSocket().getLocalPort());
			out.writeObject(message);
			out.flush();
		} 
		else 
		{
			throw new NotConnectedException();
		}
	}
	/**
	 * Recebe uma mensagem. (implementa��o do lado do cliente).
	 * 
	 * @return Retorna a mensagem recebida.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws NotConnectedException
	 */
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
	/**
	 * Fecha uma conex�o. (implementa��o do lado do cliente).
	 * 
	 * @throws IOException
	 * @throws NotConnectedException
	 */
	private void clientClose() throws IOException, NotConnectedException 
	{
		if(!socket.isClosed()) 
		{
			if (socket != null) 
			{
				String source = socket.getLocalAddress().getHostAddress();
				TCPControlMessage closeMessage = new TCPControlMessage(TCPControlMessage.CLOSE_CONNECTION,SERVER,source);
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
	/**
	 * M�todo que envia um objeto, atrav�s de um host e de uma porta. 
	 * (implementa��o do lado do servidor).
	 *    
	 * @param obj objeto a ser enviado.
	 * @param destinationIP endere�o de IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws IOException
	 * @throws NotExistingClientException
	 */
	private void serverSendTo(Object msg, String destinationIP, int destinationPort) 
	throws IOException, NotExistingClientException 
	{
		boolean found = false;
		Iterator<TCPHandler> it = connections.values().iterator();
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
	/**
	 * M�todo que envia um objeto para todos os clientes conectados.
	 * (implementa��o do lado do servidor).
	 *    
	 * @param msg objeto a ser enviado.
	 * @throws IOException
	 */
	private void serverSendToAll(Object msg) throws IOException 
	{
		Iterator<TCPHandler> ite = connections.values().iterator();
		while(ite.hasNext()) 
		{
			TCPHandler client = ite.next();
			client.send(msg);
		}		
	}
	/**
	 * Recebe uma mensagem. (implementa��o do lado do servidor).
	 * 
	 * @return Retorna a mensagem recebida.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private synchronized Message serverReceive() throws IOException, ClassNotFoundException 
	{
		while(this.messages.isEmpty()); //Espera receber uma mensagem
		Message msg = null;

		//Pego o Set de chaves depois pego seu tamanho e diminuo de 1.
		//O Set se comporta como uma pilha entaum a menor chave(a mensagem mais antiga) fica na ultima posicao do array
		int position = this.messages.keySet().toArray().length - 1;
		Integer pos = (Integer) this.messages.keySet().toArray()[position];
		msg = this.messages.get(pos);
		msg.checkMessage();
		this.messages.remove(pos);

		return msg;
	}
	/**
	 * Fecha uma conex�o com um cliente. (implementa��o do lado do servidor).
	 * @param destination endere�o IP do cliente a ser disconectado.
	 * @param destinationPort porta do cliente a ser disconectado.
	 * @throws Throwable
	 */
	private void serverClose(String destination, int destinationPort) throws Throwable 
	{
		boolean found = false;
		Iterator<TCPHandler> it = connections.values().iterator();
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
	/**
	 * M�todo que indica se uma determinada porta � v�lida ou n�o.
	 * 
	 * @param port n�mero da porta a ser verificada.
	 * @return <code>true</code> se a porta for v�lida, <code>false</code> caso contr�rio.
	 */
	private boolean isValidPort(int port) 
	{
		boolean valida = true;
		if (port > 0 && port < 1023 && port > 65535) 
			valida = false;

		return valida;
	}
	/**
	 * M�todo que adiciona uma mensagem � fila de mensagens desta classe.
	 * 
	 * @param message mensagem a ser adicionada.
	 */
	public void addMessage(Message message) {
		this.messages.put(idMessage,message);
		idMessage++;
	}

	/**
	 * M�todo que remove e retorna uma determinada mensagem da fila de
	 * mensagens desta classe.
	 * 
	 * @param message mensagem a ser removida.
	 * @return Retorna a mensagem removida.
	 */
	public Message removeMessage(Message message) {
		//TODO por padr�o, os m�todos remove sempre retornam o que foi removido...
		return this.messages.remove(message);
	}
	/**
	 * M�todo que indica se h� uma conex�o estabelecida.
	 * 
	 * @return Retorna <code>true</code> se estiver conectado,
	 * <code>false</code> caso contr�rio.
	 */
	public boolean isConnected() {
		return connected;
	}
	/**
	 * M�todo que altera o status entre conectado ou n�o-conectado.
	 * 
	 * @param connected o novo status: <code>true</code> se for conectado,
	 * <code>false</code> caso contr�rio.
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	/**
	 * M�todo que indica se esta classe est� representando um servidor.
	 * 
	 * @return Retorna <code>true</code> se estiver representando um servidor,
	 * <code>false</code> caso contr�rio.
	 */
	public boolean isServer() {
		return isServer;
	}
	/**
	 * Altera a indica��o de que esta classe est� representando um servidor.
	 * 
	 * @param isServer <code>true</code> se esta classe representar um servidor,
	 * <code>false</code> caso contr�rio.
	 */
	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}
	/**
	 * Retorna a tabela de conex�es estabelecidas atrav�s de TCP.
	 * 
	 * @return Retorna o <code>connections</code>.
	 */
	public Map<Long, TCPHandler> getConnections() {
		return connections;
	}
	/**
	 * Altera a tabela de conex�es estabelecidas atrav�s de TCP. 
	 * 
	 * @param connections o novo <code>connections</code>.
	 */
	public void setConnections(Map<Long, TCPHandler> connections) {
		this.connections = connections;
	}
	/**
	 * Retorna a tabela de mensagens recebidas por esta classe TCP.
	 * 
	 * @return Retorna o <code>messages</code>.
	 */
	public Map<Integer, Message> getMessages() {
		return messages;
	}
	/**
	 * Altera a tabela de mensagens recebidas por esta classe TCP.
	 * 
	 * @param messages
	 */
	public void setMessages(Map<Integer, Message> messages) {
		this.messages = messages;
	}
	/**
	 * Retorna a identifica��o da conex�o.
	 * 
	 * @return Retorna o <code>idConnection</code>.
	 */
	public long getIdConnection() {
		return idConnection;
	}
	/**
	 * Altera a identifica��o da conex�o.
	 * 
	 * @param idConnection o novo <code>idConnection</code>. 
	 */
	public void setIdConnection(long idConnection) {
		this.idConnection = idConnection;
	}
	/**
	 * Retorna o n�mero atual usado pra identificar mensagens.
	 * 
	 * @return Retorna o <code>idMessage</code>.
	 */
	public int getIdMessage() {
		return idMessage;
	}
	/**
	 * Altera o n�mero atual usado pra identificar mensagens.
	 * 
	 * @param idMessage o novo <code>idMessage</code>.
	 */
	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}
	/**
	 * Retorna a stream de entrada associada a esta classe.
	 * 
	 * @return Retorna o <code>in</code>.
	 */
	public ObjectInputStream getIn() {
		return in;
	}
	/**
	 * Altera a stream de entrada associada a esta classe.
	 * 
	 * @param in O novo <code>in</code>.
	 */
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	/**
	 * Retorna a stream de saida associada a esta classe.
	 * 
	 * @return Retorna o <code>out</code>.
	 */
	public ObjectOutputStream getOut() {
		return out;
	}
	/**
	 * Altera a stream de saida associada a esta classe.
	 * 
	 * @param out O novo <code>out</code>.
	 */
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
	/**
	 * Retorna o socket do servidor.
	 * 
	 * @return Retorna o <code>server</code>.
	 */
	public ServerSocket getServer() {
		return server;
	}
	/**
	 * Altera o socket do servidor.
	 * 
	 * @param server o novo <code>server</code>.
	 */
	public void setServer(ServerSocket server) {
		this.server = server;
	}
	/**
	 * Retorna o socket do cliente.
	 * 
	 * @return Retorna o <code>socket</code>.
	 */
	public Socket getSocket() {
		return socket;
	}
	/**
	 * Altera o socket do cliente.
	 * 
	 * @param socket o novo <code>socket</code>.
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
