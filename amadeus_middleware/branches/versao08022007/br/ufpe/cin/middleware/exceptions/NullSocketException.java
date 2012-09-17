package br.ufpe.cin.middleware.exceptions;

/**
 * Classe que representa a exceção de socket nulo.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class NullSocketException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe NullSocketException.
	 * 
	 */
	public NullSocketException() {
		super("Socket nulo");
	}
}
