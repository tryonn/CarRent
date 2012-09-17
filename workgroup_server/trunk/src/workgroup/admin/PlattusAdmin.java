package workgroup.admin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import workgroup.group.GroupView;
import workgroup.group.User;
import workgroup.message.AdminMessage;
import workgroup.server.ServerStateView;

public class PlattusAdmin {

	private static final int DEFAULT_SERVER_ADMIN_PORT = 1012;

	private static final int INI_PORT = 6900;
	private static final int END_PORT = 7000;
	
	private static Logger log = Logger.getLogger(PlattusAdmin.class);

	// Porta de Administração do Servidor
	private String serverHost;
	private int serverAdminPort;
	
	ServerSocket serverSocket;
	ServerStateView serverState;
	
	public PlattusAdmin(String serverHost) throws UnknownHostException, IOException {
		this(serverHost, DEFAULT_SERVER_ADMIN_PORT);
	}

	public PlattusAdmin(String serverHost, int serverPort) throws UnknownHostException, IOException {
		PropertyConfigurator.configure("log4j.properties");
		log.info("Iniciando PlattusAdmin...");
		
		// Porta de Administração do Servidor
		this.serverAdminPort = serverPort;
		
		// Tenta criar um Socket em uma porta entre 6900-7000
		int port = INI_PORT;
		boolean sucesso = false;
		sucesso = createServerSocket(port);
		while ((! sucesso ) && (port < END_PORT)) {
			port = port + 1;
			sucesso = createServerSocket(port);
		}
		
		// Verifica se teve sucesso ao criar socket
		if (! sucesso) {
			throw new SocketException("Erro ao criar socket");
		}
		
		// Registra-se com o Servidor Plattus
		this.adminRegister(port);
		
		// listen
		this.listen();
	}
	
	private void adminRegister(int port) throws UnknownHostException, IOException {
		Socket socket = new Socket(serverHost, serverAdminPort);
		OutputStream outToServer = socket.getOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(outToServer);

		// Fazendo registro
		AdminMessage message = new AdminMessage(AdminMessage.ADMIN_REGISTER);
		message.addParameter("admin");
		message.addParameter("admin");
		message.addParameter(String.valueOf(port));
		out.writeObject(message);	
	}

	private void listen() throws IOException {
		while (true) {
			Socket socket = serverSocket.accept();
			log.debug("Conexão aceita de " + socket);
			//new ServerStateChangeHandler(socket);
			serverStateChange(socket);
		}
	}

	private void serverStateChange(Socket socket) {
		ObjectInputStream in;
		try {
			// Lê messagem de modelo
			in = new ObjectInputStream(socket.getInputStream());
			AdminMessage message = (AdminMessage) in.readObject();
			ServerStateView state = (ServerStateView) message.getObject();
			in.close();
			this.serverState = state;
			modelChanged();
		} catch (IOException e) {
			log.error("Erro ao receber dados do servidor: Erro de E/S.", e);
		} catch (ClassNotFoundException e) {
			log.error("Erro ao receber dados do servidor: Classe não encontrada.", e);
		}
	}
	
	private void modelChanged() {
		String[] groupList = serverState.getGroupList(); 
		for (String group : groupList) {
			// Nome do Grupo
			GroupView groupView = serverState.getGroupView(group);
			System.out.println("Grupo: " + groupView.getGroupName());
			
			// Lista de usuários
			List<User> userList = groupView.getuserList();
			System.out.println("Usuários: " + userList);
		}
	}

	public static void main(String[] args) {
		try {
			PlattusAdmin plattusAdmin = new PlattusAdmin(args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean createServerSocket(int port) {
		boolean sucesso = true;
		try {
			serverSocket = new ServerSocket(port);
			log.debug("Socket criado na porta: " + port);
		} catch (IOException e) {
			log.debug("Erro ao criar socket na porta: " + port);
			sucesso = false;
		}
		return sucesso;
	}
}

