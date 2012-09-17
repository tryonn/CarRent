package exception;

/**
 * Classe lan�ada quando ocorre uma exce��o relativa � cria��o de uma a��o
 * 
 * @author amadeus
 * @version 1.0
 */
public class ActionCreateException extends Exception {
	
	/**
	 * Construtor da classe
	 *  
	 * @param message Mensagem a ser mostrada ao usu�rio
	 */
	public ActionCreateException(String message) {
		super(message);
	}

	/**
	 * Cosntrutor da classe
	 * 
	 * @param message Mensagem a ser mostrada ao usu�rio
	 * @param throwable Par�metro utilizado no contrutor pai desta classe
	 */
	public ActionCreateException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
