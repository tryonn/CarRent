package br.ufpe.cin.middleware.exceptions;

/**
 * Classe que representa a exceção de operação não implementada.
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
	 * @param operationName - nome da operação.
	 * @param protocol - nome do módulo.
	 */
	public OperationNotImplemmentedException(String operationName, String protocol) {
		super("A operação " + operationName + " não está implementada para o módulo " + protocol);
	}
}
