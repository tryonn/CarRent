package controller.pendulo.action;

import model.Model;
import util.Action;
import util.resource.Resource;
import exception.ActionCreateException;
import exception.ActionExecuteException;

/**
 * Classe respons�vel por todas as a��es que ocorrem no programa
 * 
 * @author amadeus
 * @version 1.0
 */
public abstract class PenduloAction extends Action {
	
	private Class target;
	
	/**
	 * Construtor da classe
	 * 
	 * @param target Modelo alvo da a��o
	 * @throws ActionCreateException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a cria��o de uma a��o
	 */
	protected PenduloAction(Class target) throws ActionCreateException {
		if (! (Model.class.isAssignableFrom(target))) {
			throw new ActionCreateException(Resource.targetClassIncompatible);
		}
		this.target = target;
	}
	
	/**
	 * M�todo que executa uma a��o 
	 * 
	 * @param model Modelo que executar� a a��o
	 * @return boolean O sucesso da opera��o
	 */
	public final boolean execute(Model model) throws ActionExecuteException {
		boolean resultado = false;
		if (target.isInstance(model)) {
			try {
				resultado = doAction(model);
			} catch (Exception e) {
				//e.printStackTrace();
				throw new ActionExecuteException(e.getMessage());
			}
		}
		return resultado;
	}

	/**
	 * M�todo abstrato cuja implementa��o � a execu��o de uma a��o
	 *  
	 * @param model Modelo que executar� a a��o
	 * @return boolean O sucesso da opera��o
	 */
	protected abstract boolean doAction(Model model);
}
