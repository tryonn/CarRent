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
 * Classe responsável pelo middleware da aplicação
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
	 * @param connection Conexão
	 */
	public GroupwareModel(Model model, WorkGroupConnection connection){
		this.model = model;
		this.connection = connection;
		model.addObserver(this);
		connection.addWorkGroupListener(groupListener);
	}

	/**
	 * Método que executa uma ação provinda de estímulo externo, como o pressionamento
	 * de um botão, etc
	 * 
	 * @param action A ação a ser executada
	 * @throws ActionExecuteException Exceção lançada quando algo inesperado 
	 * ocorre durante a execução da ação
	 */
	public void execute(Action action) throws ActionExecuteException {
		model.execute(action);
	}

	/**
	 * Método que manda o modelo pela rede para que todos os que estejam conectados 
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
	 * Método que retorna o modelo em evidência
	 * 
	 * @return Model Modelo em evidência
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Método que substitui o modelo atual e faz com que esta classe seja observadora 
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
	 * Classe privada responsável pelas ações do middleware
	 * 
	 * @author amadeus
	 * @version 1.0
	 */
	private class GroupModelListener extends WorkGroupAdapter {

		/**
		 * Método que atualiza o modelo com a mensagem passado como parâmetro
		 * 
		 * @param message Mensagem através da qual pode-se obter o modelo correspondente
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
