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
 * Classe controladora principal respons�vel pelas a��es
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
	* Especifica se o usu�rio atual � o dono do modelo
	*/
	private boolean modelOwner = false;  
	
	private GroupControllerListener listener = new GroupControllerListener();
	
	/**
	 * Construtor da classe
	 * 
	 * @param model Modelo em evid�ncia
	 * @throws ActionCreateException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a cria��o de uma a��o
	 */
	public PenduloMainControler(Model model) throws ActionCreateException {
		this.model = model;
		next = new NextAction();
		prior = new PriorAction();
	}
	
	/**
	 * Construtor da classe
	 * 
	 * @param model Modelo em evid�ncia
	 * @param connection Conex�o (somente para o usu�rio dono do modelo)
	 * @throws ActionCreateException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a cria��o de uma a��o
	 */
	public PenduloMainControler(Model model, WorkGroupConnection connection) throws ActionCreateException {
		this(model);
		connection.addWorkGroupListener(listener);
	}

	/**
	 * M�todo que envia ao modelo uma ordem de execu��o da a��o de atualizar a altura do aparato
	 * 
	 * @param altura Nova altura do aparato
	 * @throws ActionCreateException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a cria��o da a��o de atualizar a altura do aparato
	 * @throws ActionExecuteException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a execu��o da a��o de atualizar a altura do aparato
	 */
	public void atualizaAltura(String altura) throws ActionCreateException, ActionExecuteException {
		model.execute(new atualizaAlturaAction(altura));
	}

	/**
	 * M�todo que envia ao modelo uma ordem de execu��o da a��o de incrementar o passo
	 * 
	 * @throws ActionExecuteException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a execu��o da a��o de incremento do passo
	 */
	public void next() throws ActionExecuteException {
		model.execute(next);
	}

	/**
	 * M�todo que envia ao modelo uma ordem de execu��o da a��o de decrementar o passo
	 * 
	 * @throws ActionExecuteException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a execu��o da a��o de decremento do passo
	 */
	public void prior() throws ActionExecuteException {
		model.execute(prior);
	}

	/**
	 * M�todo que diz se o usu�rio � o dono do modelo
	 * 
	 * @return boolean Se o usu�rio � ou n�o o dono do modelo
	 */
	public boolean isOwner() {
		return modelOwner;
	}

	/**
	 * M�todo que atualiza o status do usu�rio como dono do modelo ou n�o
	 * 
	 * @param modelOwner Se o usu�rio vai ou n�o ser o dono do modelo
	 */
	private void setOwner(boolean modelOwner) {
		this.modelOwner = modelOwner;
	}

	/**
	 * Classe respons�vel por notificar os observadores quando algu�m toma o 
	 * controle do modelo
	 * 
	 * @author amadeus
	 * @version 1.0
	 */
	private class GroupControllerListener extends WorkGroupAdapter {

		/**
		 * M�todo que notifica os observadores quando algu�m toma o 
		 * controle do modelo
		 */
		public void onTakeControl() {
			setOwner(true);
			setChanged();
			notifyObservers();
		}
		
	}

}
