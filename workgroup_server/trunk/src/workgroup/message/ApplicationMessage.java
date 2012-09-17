package workgroup.message;

import java.io.Serializable;

/**
 * Classe responsável por construir uma mensagem da aplicação
 * 
 * @author amadeus
 *
 */
public class ApplicationMessage extends Message {

	private static final long serialVersionUID = 13L;

	/**
	 * Construtor da classe
	 * 
	 * @param object Mensagem da aplicação (ver construtor de Mensagem)
	 */
	public ApplicationMessage(Serializable object) {
		super(object);
	}
}
