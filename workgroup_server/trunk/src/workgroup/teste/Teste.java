package workgroup.teste;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import workgroup.client.WorkGroupAdapter;
import workgroup.client.WorkGroupConnection;
import workgroup.ui.WGChatPanel;
import workgroup.ui.WGUserListPanel;

public class Teste extends JFrame {

	/**
	* Reponsável pela conexão com o servidor de grupos
	*/
	private WorkGroupConnection connection;
		
	/**
	 * Componente Lista de Usuarios 
	 */
	private WGUserListPanel userListPanel;

	public Teste() throws UnknownHostException, IOException {
		connection = new WorkGroupConnection("localhost", new WorkGroupAdapter());
		userListPanel = new WGUserListPanel(connection);
		
		getContentPane().add(userListPanel);
		
		connection.open("Teste", "cin", "Enoque", "senha");
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Teste teste = new Teste();
		teste.setVisible(true);
	}

}
