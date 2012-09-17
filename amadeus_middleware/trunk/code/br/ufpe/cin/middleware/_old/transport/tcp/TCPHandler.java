package br.ufpe.cin.middleware._old.transport.tcp;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.exceptions.NotExistingClientException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.objects.controlMessages.TCPControlMessage;
import br.ufpe.cin.middleware.util.Debug;

/**
 * Classe responsável por lidar com eventos ocorridos no lado do cliente 
 * e informar ao servidor.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class TCPHandler extends Thread {

	private static final String PROTOCOL = "TCP";
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private TCP serverSource; 
	/**
	 * Construtor que cria um novo TCPHandler.
	 * 
	 * @param socket - Socket associado a esta conexão.
	 * @param source - ServerTCP ao qual este socket esta conectado.
	 * 
	 * @throws IOException - Exceção levantada quando ocorre erro de IO.
	 */
	public TCPHandler(Socket socket, TCP source) throws IOException {
		this.socket = socket;		
		this.serverSource = source;
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		this.in = new ObjectInputStream(this.socket.getInputStream());
		this.start();
	}
	/**
	 * Método que fecha a conexão deste TCPHandler com o servidor.
	 * 
	 * @throws Throwable 
	 */
	public void closeConnection() throws Throwable {
		this.in.close();
		this.out.close();
		this.socket.close();
		Debug.warning(TCPHandler.class, "Socket Fechado: " + this.socket.isClosed());
		this.serverSource.getConnections().remove(this);
		this.serverSource.setConnected(false);
		this.interrupt();
	}
	/**
	 * Método que verifica se o socket do cliente associado a este TCPHandler
	 * está conectado.
	 * 
	 * @return - Retorna <code>true</code> caso ele esteja conectado, caso contrário retorna <code>false</code>.
	 */
	public boolean isConnected() {
		return this.socket.isConnected();
	}
	/**
	 * Método que envia uma mensagem para o cliente associado a este TCPHandler.
	 * 
	 * @param msg - Objeto a ser enviado.
	 * @throws IOException - Exceção levantada quando ocorre erro de IO.
	 */
	public void send(Object msg) throws IOException {
		String destination = socket.getInetAddress().getHostAddress();
		String source = socket.getLocalAddress().getHostAddress();
		Message message = null;
		if(msg instanceof Message) {
			Message changedOriginal = (Message)msg;
			changedOriginal.setDestinationIP(changedOriginal.getSourceIP());
			changedOriginal.setDestinationPort(changedOriginal.getSourcePort());
			changedOriginal.setSourceIP(changedOriginal.getDestinationIP());
			changedOriginal.setSourcePort(changedOriginal.getDestinationPort());
			message = changedOriginal;
		} else {
			message = new Message(PROTOCOL,msg,destination,source);
		}
		out.writeObject(message);
		out.flush();		
	}
	/**
	 * Método que o TCPHandler recebe uma mensagem do Cliente associado a ele.
	 * 
	 * @throws Throwable 
	 */
	public void receive() throws Throwable {
		Message message = (Message) this.in.readObject();
		if(message instanceof TCPControlMessage) {
			TCPControlMessage close = (TCPControlMessage) message;
			int type = close.getMessageType();
			if(type == TCPControlMessage.CLOSE_CONNECTION) {
				this.closeConnection();
			}
		} else {
			if(message.getDestinationIP().equals("Server")) {
				this.serverSource.addMessage(message);
			} else if(message.getDestinationIP().equals("ToAll")) {
				this.serverSource.sendToAll(message);
			} else {
				this.serverSource.sendTo(message,message.getDestinationIP(),message.getDestinationPort());
			}
		}
	}
	/**
	 * Método que define o comportamento da thread TCPHandler.
	 * 
	 */
	public void run() {
            while(!this.socket.isClosed()) {
				try {
					this.receive();
				} catch(SocketException e) {
					synchronized (this) {
						try {
							TCPControlMessage tcp = new TCPControlMessage(TCPControlMessage.CLOSE_CONNECTION,
									socket.getInetAddress().getHostName(),
									socket.getLocalAddress().getHostName(),
									serverSource.getServer().getLocalPort(),
									socket.getLocalPort()
									);
							Message msg = new Message("TCP",tcp,
									socket.getInetAddress().getHostName(),
									socket.getLocalAddress().getHostName(),
									serverSource.getServer().getLocalPort(),
									socket.getLocalPort()
							);
							this.serverSource.addMessage(msg);
							this.interrupt();
							break;
						} catch (Throwable e1) {
							Debug.printStack(e1);
						}
					}
				}catch(EOFException e){
					this.interrupt();
				}catch (IOException e) {
					Debug.printStack(e);
				} catch (ClassNotFoundException e) {
					Debug.printStack(e);
				} catch (NotExistingClientException e) {
					Debug.printStack(e);
				} catch (NotConnectedException e) {
					Debug.printStack(e);
				} catch (InvalidIPException e) {
					Debug.printStack(e);
				} catch (Throwable e) {
					Debug.printStack(e);
				}
            }
	}
	/**
	 * Método que retorna o ObjectInputStream do TCPHandler.
	 * 
	 * @return Retorna o ObjectInputStream do TCPHandler.
	 */
	public ObjectInputStream getIn() {
		return in;
	}
	/**
	 * Método que modifica o ObjectInputStream do TCPHandler.
	 * 
	 * @param in Novo ObjectInputStream do TCPHandler.
	 */
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	/**
	 * Método que retorna o ObjectOutputStream do TCPHandler.
	 * 
	 * @return Retorna o ObjectOutputStream do TCPHandler.
	 */
	public ObjectOutputStream getOut() {
		return out;
	}
	/**
	 * Método que modifica o ObjectOutputStream do TCPHandler.
	 * 
	 * @param out Novo ObjectOutputStream do TCPHandler.
	 */
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
	/**
	 * Método que retorna o socket associado ao TCPHandler.
	 * 
	 * @return Retorna o socket associado ao TCPHandler.
	 */
	public Socket getSocket() {
		return socket;
	}
	/**
	 * Método que modifica o socket associado ao TCPHandler.
	 * 
	 * @param socket Novo socket associado ao TCPHandler.
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	/**
	 * Método que retorna a referência ao Servidor ao qual o TCPHandler está associado.
	 * 
	 * @return Retorna a referência ao Servidor.
	 */
	public TCPMBean getSource() {
		return serverSource;
	}
	/**
	 * Método que modifica a referência ao Servidor ao qual o TCPHandler está associado.
	 *
	 * @param source Nova referência ao servidor.
	 */
	public void setSource(TCP source) {
		this.serverSource = source;
	}
}
