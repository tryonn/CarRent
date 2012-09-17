package workgroup.exception;

/**
 * Classe responsável por lançar a exceção quando o funcionamento da autenticação 
 * do usuário não se dá de maneria esperada
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
