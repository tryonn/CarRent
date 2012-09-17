package workgroup.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import workgroup.group.GroupManager;
import workgroup.handler.LoginHandler;
import workgroup.server.services.Service;

public class PlattusServer {
	
	private static final int DEFAULT_PORT = 1010;
	private static final int ADMIN_PORT = 1012;
	
	
	private static Logger log = Logger.getLogger(PlattusServer.class);
	
	private ServerModel serverModel; 
	
	public PlattusServer() throws Exception {
		PropertyConfigurator.configure(PlattusServer.class.getResource("log4j.properties"));
		log.info("Iniciando Servidor...");
		serverModel = ServerModel.getInstance();
		serverModel.setServer(this);
		
		// Inicia Serviços
		Service groupService = new WorkGroupService(this, DEFAULT_PORT);
		groupService.start();
		Service adminService = new AdminService(this, ADMIN_PORT);
		adminService.start();
		
		serverModel.addService(groupService);
		serverModel.addService(adminService);
		
		//listen(DEFAULT_PORT);	
	}
	
	private void listen(int port) throws IOException {
		log.debug("Criando socket na porta (" + port + ")");
		ServerSocket serverSocket = new ServerSocket(port);
		log.info("Servidor atendendo na porta (" + port + ")");
			
		while (true) {
			Socket socket = serverSocket.accept();
			log.debug("Conexão aceita de " + socket + ", processando login...");
			new LoginHandler(this, socket);
		}
	}
	
	public void addCliente(String grupo, String login, Socket clientSocket){
		try {
			log.debug("adicionando usuario [" + login + "]");
			GroupManager manager = ServerModel.getInstance().getGroupManager(grupo);
			if (manager == null) {
				log.debug("Criando Group Manager para o grupo [" + grupo + "]");
				manager = serverModel.addGroup(grupo);
			}
			manager.addCliente(login, clientSocket);
		} catch (IOException e) {
			log.error("Erro ao adicionar usuario");
		}
	}

	public void log(Exception ex) {
		info("Erro: " + ex.getMessage());
	}

	public void log(String msg) {
		info(msg);
	}

	public void info(String msg) {
		log.info(msg);
	}
	
	public void info(String msg, Exception ex) {
		log.info(msg + ": " + ex.getMessage());
		if (log.getEffectiveLevel() == Level.DEBUG) {
			ex.printStackTrace();
		}
	}
	
	public void debug(String msg) {
		log.debug(msg);
	}
	
	public void debug(String msg, Exception ex) {
		log.debug(msg, ex);
	}
	
	public Logger getLogger() {
		return log;
	}

	public void error(Exception ex) {
		log.error(ex);
		
	}

	public static void main(String[] args) throws Exception {
		new PlattusServer();
	}
}
