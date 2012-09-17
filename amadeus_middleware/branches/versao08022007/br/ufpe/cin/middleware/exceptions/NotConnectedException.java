package br.ufpe.cin.middleware.exceptions;

/**
 * Classe que representa a exceção de socket não conectado.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class NotConnectedException extends Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * Construtor da classe NotConnectedException.
	 *
	 */
	public NotConnectedException() {
		super("Socket não conectado");
	}
}
