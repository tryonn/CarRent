package workgroup.message;

import java.io.Serializable;

/**
 * Classe respons�vel por construir uma mensagem da aplica��o
 * 
 * @author amadeus
 *
 */
public class ApplicationMessage extends Message {

	private static final long serialVersionUID = 13L;

	/**
	 * Construtor da classe
	 * 
	 * @param object Mensagem da aplica��o (ver construtor de Mensagem)
	 */
	public ApplicationMessage(Serializable object) {
		super(object);
	}
}
