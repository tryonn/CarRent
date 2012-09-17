package br.ufpe.cin.middleware._old;

import java.io.Serializable;

/**
 * Classe que representa o retorno de uma chamada remota.
 * 
 * @author Bruno Barros (blbs@cin.ufpe.br)
 * @author Rebeka Gomes (rgo2@cin.ufpe.br)
 * 
 */
public class RemoteCallReply implements Serializable {

	private static final long serialVersionUID = 1L;

	public Object content;
	
	/**
	 * Construtor da classe RemoteCallReply.
	 * 
	 * @param content objeto retornado pela execução da chamada remota.
	 */
	public RemoteCallReply(Object content) {
		this.content = content;
	}
}
