package br.ufpe.cin.middleware.exceptions;

/**
 * Classe que representa a exceção de cliente destino não existente.
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
		super("Cliente destino não existente");
	}

}
