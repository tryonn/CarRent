package br.ufpe.cin.middleware.exceptions;

/**
 * Classe que representa a exceção de porta inválida.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class InvalidPortException extends Exception {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Construtor da classe InvalidPortException.
	 *
	 */
	public InvalidPortException() {
		super("Porta invalida!");
	}
}
