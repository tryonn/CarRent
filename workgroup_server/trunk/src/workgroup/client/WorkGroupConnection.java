package workgroup.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import workgroup.group.GroupView;
import workgroup.message.ApplicationMessage;
import workgroup.message.ChatMessage;
import workgroup.message.ControlMessage;
import workgroup.message.Message;
import workgroup.message.ModelMessage;

public class WorkGroupConnection extends Thread {
	
	private static Logger log = Logger.getLogger(WorkGroupConnection.class);

	private static final int DEFAULT_PORT = 1010; 
	
	private String serverAddress; 
	private int port;
	private boolean openned = false;
	
	// Messages Listeners
	private WorkGroupManyListener listeners = new WorkGroupManyListener(); 
	
	// Socket & Streams
	private Socket socket;
	private OutputStream outToServer;
	private ObjectOutputStream out;
	
	public WorkGroupConnection(String serverAddress, WorkGroupListener listener) {
		this(serverAddress, DEFAULT_PORT, listener);
		setDaemon(true);
	}
	
	public WorkGroupConnection(String serverAddress, int port, WorkGroupListener listener) {
		super("WorkGroupConnection");
		Properties p = new Properties();
		PropertyConfigurator.configure(WorkGroupConnection.class.getResource("log4j.properties"));
		this.serverAddress = serverAddress;
		this.port = port;
		this.addWorkGroupListener(listener);
	}
	
	public void open(String aplic, String grupo, String login, String senha) throws UnknownHostException, IOException {
		socket = new Socket(this.serverAddress, this.port);
		outToServer = socket.getOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(outToServer);
		this.start();
		
		// Fazendo Login
		ControlMessage message = new ControlMessage(ControlMessage.CONNECT);
		message.addParameter(aplic);
		message.addParameter(grupo);
		message.addParameter(login);
		message.addParameter(senha);
		out.writeObject(message);
		//out.flush();
		
		setOpenned(true);
		
		this.out = new ObjectOutputStream(outToServer);
	}
	
	private void receive(Message message) {
		if (message instanceof ControlMessage) {
			receive( (ControlMessage)message );
		} else {
			log.debug("Mensagem recebida");
			listeners.onMessage(message);
		}
	}
	
	private void receive(ControlMessage message) {
		log.debug("Mensagem de controle recebida");
		if (message.getType() == ControlMessage.VIEW_CHANGE) {
			GroupView view = (GroupView) message.getObject();
			listeners.onChangeGroupView(view);
		} else if (message.getType() == ControlMessage.TAKE_FLOOR_CONTROL) {
			listeners.onTakeControl();
		}
	}

	public void send(Message message) throws IOException {
		if (message != null) {
			log.debug("Enviando mensagem");
			checkOpenned();
			//ObjectOutputStream out = new ObjectOutputStream(outToServer);
			out.reset();
			out.writeObject(message);
			//out.flush();
		}
	}
	
	public void sendModelMessage(Serializable model) throws IOException {
		send(new ModelMessage(model));
	}
	
	public void sendChatMessage(String message) throws IOException {
		send(new ChatMessage(message));
	}

	public void sendApplicationMessage(Serializable obj) throws IOException {
		send(new ApplicationMessage(obj));
	}
	
	public void requestFloorControl() throws IOException {
		ControlMessage message = new ControlMessage(ControlMessage.REQUEST_FLOOR_CONTROL);
		send(message);
	}

	public void leaveFloorControl() throws IOException {
		ControlMessage message = new ControlMessage(ControlMessage.LEAVE_FLOOR_CONTROL);
		send(message);
	}

	private void checkOpenned() throws IOException {
		if (! isOpenned()) {
			log.warn("Conexão fechada!");
			throw new IOException("Conexão fechada");
		}
	}

	public void close() {
		if (socket != null) {
			log.debug("Fechando Socket...");
			try {
				socket.close();
				log.debug("Socket Fechado");
			} catch( IOException e ) {
				log.warn("Erro fechando socket", e);
			}
		}
		setOpenned(false);
		listeners.onClose();
	}

	private void listenMessage() {
		try {

			ObjectInputStream in  = new ObjectInputStream(socket.getInputStream());

			while (true) {
				try {
					Message message = (Message) in.readObject();
					receive(message);
				} catch (ClassNotFoundException e) {
					log.warn("Erro não tratado: " + e.getMessage());
				}
			}

		} catch (EOFException e) {
			log.warn("Erro não tratado: " + e.getMessage());
		} catch (IOException e) {
			log.error("Erro de I/O, o socket será fechado: " + e.getMessage());
			if (log.getEffectiveLevel() == Level.DEBUG) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void run() {
		listenMessage();
	}
	
	/*
	 *   Metodos relacionados com os listeners 
	 */
	 
	public void addWorkGroupListener(WorkGroupListener listener) {
		this.listeners.add(listener);
	}

	public void removeWorkGroupListener(WorkGroupListener listener) {
		this.listeners.remove(listener);
	}
	
	public boolean isOpenned() {
		return openned;
	}
	protected void setOpenned(boolean openned) {
		this.openned = openned;
	}

}

class WorkGroupManyListener implements WorkGroupListener {
	
	// Messages Listeners
	private List<WorkGroupListener> listeners = new ArrayList<WorkGroupListener>();
	
	public void add(WorkGroupListener listener) {
		listeners.add(listener);
	}

	public void remove(WorkGroupListener listener) {
		listeners.remove(listener);
	}

	public void onMessage(Message message) {
		Iterator<WorkGroupListener> it = this.listeners.iterator();
		while (it.hasNext()) {
			WorkGroupListener listener = it.next();
			listener.onMessage(message);
		}
	}

	public void onClose() {
		Iterator<WorkGroupListener> it = this.listeners.iterator();
		while (it.hasNext()) {
			WorkGroupListener listener = it.next();
			listener.onClose();
		}
	}

	public void onChangeGroupView(GroupView view) {
		Iterator<WorkGroupListener> it = this.listeners.iterator();
		while (it.hasNext()) {
			WorkGroupListener listener = it.next();
			listener.onChangeGroupView(view);
		}
	}

	public void onTakeControl() {
		Iterator<WorkGroupListener> it = this.listeners.iterator();
		while (it.hasNext()) {
			WorkGroupListener listener = it.next();
			listener.onTakeControl();
		}
	}
}

