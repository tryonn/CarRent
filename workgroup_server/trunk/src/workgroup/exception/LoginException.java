package workgroup.exception;

/**
 * Classe respons�vel por lan�ar a exce��o quando o funcionamento da autentica��o 
 * do usu�rio n�o se d� de maneria esperada
 * @author amadeus
 *
 */
public class LoginException extends Exception {

	
	private static final long serialVersionUID = 13L;

	/**
	 * Construtor da classe
	 *
	 */
	public LoginException() {
		super("Login ou senha incorreto");
	}
	
}
