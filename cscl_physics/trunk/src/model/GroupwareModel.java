package model;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import exception.ActionExecuteException;
import util.Action;
import util.ModelObserver;
import workgroup.client.WorkGroupAdapter;
import workgroup.client.WorkGroupConnection;
import workgroup.client.WorkGroupListener;
import workgroup.group.GroupView;
import workgroup.message.Message;
import workgroup.message.ModelMessage;

/**
 * Classe respons�vel pelo middleware da aplica��o
 * 
 * @author amadeus
 * @version 1.0
 */
public class GroupwareModel extends Model implements ModelObserver {
	
	private Model model;
	private WorkGroupConnection connection;
	private GroupModelListener groupListener = new GroupModelListener();
	
	/**
	 * Construtor da classe
	 * 
	 * @param model Modelo
	 * @param connection Conex�o
	 */
	public GroupwareModel(Model model, WorkGroupConnection connection){
		this.model = model;
		this.connection = connection;
		model.addObserver(this);
		connection.addWorkGroupListener(groupListener);
	}

	/**
	 * M�todo que executa uma a��o provinda de est�mulo externo, como o pressionamento
	 * de um bot�o, etc
	 * 
	 * @param action A a��o a ser executada
	 * @throws ActionExecuteException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a execu��o da a��o
	 */
	public void execute(Action action) throws ActionExecuteException {
		model.execute(action);
	}

	/**
	 * M�todo que manda o modelo pela rede para que todos os que estejam conectados 
	 * tenham seus modelos atualizados
	 * 
	 * @param model Modelo atual a ser passado pela rede
	 */
	public void update(Model model) {
		notifyObservers(model);
		try {
			connection.sendModelMessage(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * M�todo que retorna o modelo em evid�ncia
	 * 
	 * @return Model Modelo em evid�ncia
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * M�todo que substitui o modelo atual e faz com que esta classe seja observadora 
	 * deste modelo
	 *  
	 * @param model Modelo atual
	 */
	public void setModel(Model model) {
		this.model = model;
		model.addObserver(this);
		notifyObservers(model);
	}

	/**
	 * Classe privada respons�vel pelas a��es do middleware
	 * 
	 * @author amadeus
	 * @version 1.0
	 */
	private class GroupModelListener extends WorkGroupAdapter {

		/**
		 * M�todo que atualiza o modelo com a mensagem passado como par�metro
		 * 
		 * @param message Mensagem atrav�s da qual pode-se obter o modelo correspondente
		 */
		public void onMessage(Message message) {
			if (message instanceof ModelMessage) {
				ModelMessage modelMessage = (ModelMessage) message;
				Model remoteModel = (Model) modelMessage.getObject(); 
				setModel(remoteModel);
			}
		}
		
	}

}
