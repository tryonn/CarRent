package gui;

import java.awt.Dimension;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.GroupwareModel;
import model.Model;
import model.pendulo.PenduloMainModel;
import util.resource.Resource;
import view.pendulo.PenduloMainView;
import workgroup.client.WorkGroupAdapter;
import workgroup.client.WorkGroupConnection;
import workgroup.ui.WGChatPanel;
import workgroup.ui.WGUserListPanel;
import controller.pendulo.PenduloMainControler;
import exception.ActionCreateException;
/**
 * Classe principal da aplicação de Ensino Colaborativo
 * de Física Experimental à Distância
 * 
 * @author amadeus
 * @version 1.0
 */

public class MainFrame extends JFrame {

	
	private PenduloMainControler controller;
	private PenduloMainView view;
	private Model model;

	/**
	* Reponsável pela conexão com o servidor de grupos
	*/
	private WorkGroupConnection connection;
		
	/**
	 * Componente de Chat 
	 */
	private WGChatPanel chatPanel;

	/**
	 * Componente Lista de Usuarios 
	 */
	private WGUserListPanel userListPanel;

	/**
	* Especifica se o sistema vai ser usado em rede
	*/
	public static boolean groupware = true;
	
	/**
	 * Construtor do frame principal
	 * 
	 * @throws ActionCreateException Exceção lançada quando algo inesperado 
	 * ocorre durante a criação de uma ação
	 */
    public MainFrame() throws ActionCreateException {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Resource.title);

    	this.setSize(new Dimension(800, 660));
        
        
		if (groupware) {
			String servidor = null;
			String grupo = null;
			String usuario = null;
			
			
			servidor = JOptionPane.showInputDialog(Resource.hostAddress, Resource.localhost);
			grupo = Resource.group;
			usuario = JOptionPane.showInputDialog(Resource.userLogin, "");
				
			if (usuario == null) {
				System.exit(1);
			}

	        setTitle(Resource.title + " [" + usuario + "@" + grupo + "]");
			connection = new WorkGroupConnection(servidor, new WorkGroupAdapter());

			
			try {
				
				model = new GroupwareModel(new PenduloMainModel(), connection);
				controller = new PenduloMainControler(model, connection);
				view = new PenduloMainView(controller, usuario, connection);
				
				// Abre a conexão com o Servidor Plattus
				connection.open(Resource.openConnectionServerPlattus, grupo, usuario, "");
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, Resource.serverNotFound);
				System.exit(1);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, Resource.connectionError);
				System.exit(1);
			}

		} else {
	        model = new PenduloMainModel();
			controller = new PenduloMainControler(model);
			view = new PenduloMainView(controller);
		}
		
		model.addObserver(view);
        
        this.setContentPane(view);
    }
    
}
