package workgroup.message;

/**
 * Classe responsável pela construção da mensagem de erro do servidor
 * quando o funcionamento não se dá da maneira esperada
 * 
 * @author amadeus
 *
 */
public class ErrorMessage extends Message {

	private static final long serialVersionUID = 13L;

	/**
	 * Construtor da classe
	 *  
	 * @param errorMessage Mensagem a ser enviada pelo servidor (ver construtor de Mensagem)
	 */
	public ErrorMessage(String errorMessage) {
		super(errorMessage);
	}
}
