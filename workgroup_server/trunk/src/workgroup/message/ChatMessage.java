package workgroup.message;

/**
 * Classe responsável pela construção da mensagem de chat
 * 
 * @author amadeus
 *
 */
public class ChatMessage extends Message {

	private static final long serialVersionUID = 13L;

	/**
	 * Construtor da classe
	 * 
	 * @param message Mensagem de chat (ver construtor de Mensagem)
	 */
	public ChatMessage(String message) {
		super(message);
	}
}
