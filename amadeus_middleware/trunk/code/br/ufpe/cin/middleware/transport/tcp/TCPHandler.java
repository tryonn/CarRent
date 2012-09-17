package br.ufpe.cin.middleware.transport.tcp;

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

public class TCPHandler extends Thread {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private TCP serverSource; 
	
	public TCPHandler(Socket socket, TCP transport) throws IOException {
		this.socket = socket;		
		this.serverSource = transport;
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		this.in = new ObjectInputStream(this.socket.getInputStream());
		this.start();
	}
	
	public void closeConnection() throws Throwable {
		this.in.close();
		this.out.close();
		this.socket.close();
		Debug.warning(TCPHandler.class, "Socket Fechado: " + this.socket.isClosed());
		this.serverSource.getConnections().remove(this);
		this.serverSource.setConnected(false);
		this.interrupt();
	}
	
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
			message = new Message("TCP",msg,destination,source);
		}
		out.writeObject(message);
		out.flush();		
	}

	public void receive() throws Throwable {
		Message message = (Message) this.in.readObject();
		if(message instanceof TCPControlMessage) {
			TCPControlMessage close = (TCPControlMessage) message;
			int type = close.getMessageType();
			if(type == TCPControlMessage.CLOSE_CONNECTION) {
				this.closeConnection();
			}
		} else {
			if (message.getDestinationIP().equals("Server")) {
				this.serverSource.getMessages().write(message);
			} else if(message.getDestinationIP().equals("ToAll")) {
				this.serverSource.sendToAll(message);
			} else {
				this.serverSource.sendTo(message,message.getDestinationIP(),message.getDestinationPort());
			}
		}
	}

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
							this.serverSource.getMessages().write(msg);
							this.interrupt();
							break;
						} catch (Throwable e1) {
							Debug.printStack(e1);
						}
					}
				} catch(EOFException e){
					this.interrupt();
				} catch (IOException e) {
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
					e.printStackTrace();
				}
            }
	}

	/*           GETS E SETS           */
	
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

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public TCP getSource() {
		return serverSource;
	}

	public void setSource(TCP source) {
		this.serverSource = source;
	}
}
