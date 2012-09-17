package br.ufpe.cin.middleware.exceptions;

/**
 * Classe que representa a exce��o de opera��o n�o implementada.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class OperationNotImplemmentedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor da classe OperationNotImplemmentedException.
	 * 
	 * @param operationName - nome da opera��o.
	 * @param protocol - nome do m�dulo.
	 */
	public OperationNotImplemmentedException(String operationName, String protocol) {
		super("A opera��o " + operationName + " n�o est� implementada para o m�dulo " + protocol);
	}
}
