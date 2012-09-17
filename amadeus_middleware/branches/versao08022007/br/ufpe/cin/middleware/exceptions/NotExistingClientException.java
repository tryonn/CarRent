package br.ufpe.cin.middleware.exceptions;

/**
 * Classe que representa a exce��o de cliente destino n�o existente.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class NotExistingClientException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor da classe NotExistingClientException.
	 * 
	 */
	public NotExistingClientException() {
		super("Cliente destino n�o existente");
	}

}
