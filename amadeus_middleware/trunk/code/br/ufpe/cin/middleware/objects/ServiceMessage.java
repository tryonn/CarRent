package br.ufpe.cin.middleware.objects;

import java.io.Serializable;

import br.ufpe.cin.middleware.services.naming.NamingServiceOperations;
import static br.ufpe.cin.middleware.services.naming.NamingServiceOperations.*;

/**
 * Classe que representa uma mensagem do servi�o de nomes.
 * 
 * @author Bruno Barros (blbs@cin.ufpe.br)
 * @author Rebeka Gomes (rgo2@cin.ufpe.br)
 * 
 */
public class ServiceMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private NamingServiceOperations operation;
	private Object[] args;
	private Object returnedArg;

	public ServiceMessage(NamingServiceOperations op, Object... args) {
		this.operation = op;
		if (op == BIND || op == REBIND) {
			if (args.length != 2) {
				throw new IllegalArgumentException("Argumentos inv�lidos!");
			}
		} else if (op == RESOLVE || op == UNBIND) {
			if (args.length != 1) {
				throw new IllegalArgumentException("Argumentos inv�lidos!");
			}
		} else {
			throw new RuntimeException("Esta opera��o n�o � permitida!");
		}
		this.args = args;
	}

	public Object[] getParameters() {
		return args;
	}

	public NamingServiceOperations getOperation() {
		return operation;
	}

	public void setOperation(NamingServiceOperations operation) {
		this.operation = operation;
	}

	public Object getReturnedArg() {
		return returnedArg;
	}

	public void setReturnedArg(Object returnedArg) {
		this.returnedArg = returnedArg;
	}

}
