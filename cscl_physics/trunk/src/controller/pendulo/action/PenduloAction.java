package controller.pendulo.action;

import model.Model;
import util.Action;
import util.resource.Resource;
import exception.ActionCreateException;
import exception.ActionExecuteException;

/**
 * Classe responsável por todas as ações que ocorrem no programa
 * 
 * @author amadeus
 * @version 1.0
 */
public abstract class PenduloAction extends Action {
	
	private Class target;
	
	/**
	 * Construtor da classe
	 * 
	 * @param target Modelo alvo da ação
	 * @throws ActionCreateException Exceção lançada quando algo inesperado 
	 * ocorre durante a criação de uma ação
	 */
	protected PenduloAction(Class target) throws ActionCreateException {
		if (! (Model.class.isAssignableFrom(target))) {
			throw new ActionCreateException(Resource.targetClassIncompatible);
		}
		this.target = target;
	}
	
	/**
	 * Método que executa uma ação 
	 * 
	 * @param model Modelo que executará a ação
	 * @return boolean O sucesso da operação
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
	 * Método abstrato cuja implementação é a execução de uma ação
	 *  
	 * @param model Modelo que executará a ação
	 * @return boolean O sucesso da operação
	 */
	protected abstract boolean doAction(Model model);
}
