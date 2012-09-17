package util;

import model.Model;
import exception.ActionExecuteException;

/**
 * Classe abstrata respons�vel pela execu��o dos m�todos do tipo Action
 * 
 * @author amadeus
 * @version 1.0
 */
public abstract class Action {
	
	/**
	 * M�todo que executa a a��o do tipo Action
	 * 
	 * @param model Modelo sobre o qual o Action vai atuar
	 * @return boolean O sucesso ou n�o da opera��o
	 * @throws ActionExecuteException Exce��o lan�ada quando uma a��o � executada e algo inesperado ocorre
	 */
	public abstract boolean execute(Model model) throws ActionExecuteException;

}
