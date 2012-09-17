package exception;

/**
 * Classe lançada quando ocorre uma exceção relativa à criação de uma ação
 * 
 * @author amadeus
 * @version 1.0
 */
public class ActionCreateException extends Exception {
	
	/**
	 * Construtor da classe
	 *  
	 * @param message Mensagem a ser mostrada ao usuário
	 */
	public ActionCreateException(String message) {
		super(message);
	}

	/**
	 * Cosntrutor da classe
	 * 
	 * @param message Mensagem a ser mostrada ao usuário
	 * @param throwable Parâmetro utilizado no contrutor pai desta classe
	 */
	public ActionCreateException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
