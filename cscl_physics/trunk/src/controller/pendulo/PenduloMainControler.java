package controller.pendulo;

import model.Model;
import workgroup.client.WorkGroupAdapter;
import workgroup.client.WorkGroupConnection;
import controller.Controller;
import controller.pendulo.action.NextAction;
import controller.pendulo.action.PriorAction;
import controller.pendulo.action.atualizaAlturaAction;
import exception.ActionCreateException;
import exception.ActionExecuteException;

/**
 * Classe controladora principal responsável pelas ações
 * 
 * @author amadeus
 * @version 1.0
 */
public class PenduloMainControler extends Controller {

	private Model model;
	
	// Action
	private static NextAction next;
	private static PriorAction prior;
	
	/**
	* Especifica se o usuário atual é o dono do modelo
	*/
	private boolean modelOwner = false;  
	
	private GroupControllerListener listener = new GroupControllerListener();
	
	/**
	 * Construtor da classe
	 * 
	 * @param model Modelo em evidência
	 * @throws ActionCreateException Exceção lançada quando algo inesperado 
	 * ocorre durante a criação de uma ação
	 */
	public PenduloMainControler(Model model) throws ActionCreateException {
		this.model = model;
		next = new NextAction();
		prior = new PriorAction();
	}
	
	/**
	 * Construtor da classe
	 * 
	 * @param model Modelo em evidência
	 * @param connection Conexão (somente para o usuário dono do modelo)
	 * @throws ActionCreateException Exceção lançada quando algo inesperado 
	 * ocorre durante a criação de uma ação
	 */
	public PenduloMainControler(Model model, WorkGroupConnection connection) throws ActionCreateException {
		this(model);
		connection.addWorkGroupListener(listener);
	}

	/**
	 * Método que envia ao modelo uma ordem de execução da ação de atualizar a altura do aparato
	 * 
	 * @param altura Nova altura do aparato
	 * @throws ActionCreateException Exceção lançada quando algo inesperado 
	 * ocorre durante a criação da ação de atualizar a altura do aparato
	 * @throws ActionExecuteException Exceção lançada quando algo inesperado 
	 * ocorre durante a execução da ação de atualizar a altura do aparato
	 */
	public void atualizaAltura(String altura) throws ActionCreateException, ActionExecuteException {
		model.execute(new atualizaAlturaAction(altura));
	}

	/**
	 * Método que envia ao modelo uma ordem de execução da ação de incrementar o passo
	 * 
	 * @throws ActionExecuteException Exceção lançada quando algo inesperado 
	 * ocorre durante a execução da ação de incremento do passo
	 */
	public void next() throws ActionExecuteException {
		model.execute(next);
	}

	/**
	 * Método que envia ao modelo uma ordem de execução da ação de decrementar o passo
	 * 
	 * @throws ActionExecuteException Exceção lançada quando algo inesperado 
	 * ocorre durante a execução da ação de decremento do passo
	 */
	public void prior() throws ActionExecuteException {
		model.execute(prior);
	}

	/**
	 * Método que diz se o usuário é o dono do modelo
	 * 
	 * @return boolean Se o usuário é ou não o dono do modelo
	 */
	public boolean isOwner() {
		return modelOwner;
	}

	/**
	 * Método que atualiza o status do usuário como dono do modelo ou não
	 * 
	 * @param modelOwner Se o usuário vai ou não ser o dono do modelo
	 */
	private void setOwner(boolean modelOwner) {
		this.modelOwner = modelOwner;
	}

	/**
	 * Classe responsável por notificar os observadores quando alguém toma o 
	 * controle do modelo
	 * 
	 * @author amadeus
	 * @version 1.0
	 */
	private class GroupControllerListener extends WorkGroupAdapter {

		/**
		 * Método que notifica os observadores quando alguém toma o 
		 * controle do modelo
		 */
		public void onTakeControl() {
			setOwner(true);
			setChanged();
			notifyObservers();
		}
		
	}

}
