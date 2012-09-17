package br.ufpe.cin.middleware.exceptions;

/**
 * Classe que representa a exce��o de IP inv�lido.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */

public class InvalidIPException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe InvalidIPException.
	 *
	 */
	public InvalidIPException () {
		super("Endereco IP invalido!");
	}
}
