package util;

import model.Model;
import exception.ActionExecuteException;

/**
 * Classe abstrata responsável pela execução dos métodos do tipo Action
 * 
 * @author amadeus
 * @version 1.0
 */
public abstract class Action {
	
	/**
	 * Método que executa a ação do tipo Action
	 * 
	 * @param model Modelo sobre o qual o Action vai atuar
	 * @return boolean O sucesso ou não da operação
	 * @throws ActionExecuteException Exceção lançada quando uma ação é executada e algo inesperado ocorre
	 */
	public abstract boolean execute(Model model) throws ActionExecuteException;

}
