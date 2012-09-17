package br.ufpe.cin.middleware.objects;

import java.io.Serializable;
import br.ufpe.cin.middleware.namingService.NamingServiceOperations;
import static br.ufpe.cin.middleware.namingService.NamingServiceOperations.*;

/**
 * Classe que representa uma mensagem do serviço de nomes.
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
				throw new IllegalArgumentException("Argumentos inválidos!");
			}
		} else if (op == RESOLVE || op == UNBIND) {
			if (args.length != 1) {
				throw new IllegalArgumentException("Argumentos inválidos!");
			}
		} else {
			throw new RuntimeException("Esta operação não é permitida!");
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
