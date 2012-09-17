package br.ufpe.cin.middleware._old;


/**
 * Interface que representa as operações de RemoteCallHandler
 * visíveis externamente.
 * 
 * @author Bruno Barros (blbs@cin.ufpe.br)
 * @author Rebeka Gomes (rgo2@cin.ufpe.br)
 *
 */
public interface RemoteCallHandlerMBean {
	
	/**
	 * Verifica uma chamada remota, retornando a resposta da chamada remota em seguida.
	 * 
	 * 
	 * @param rc - uma chamada remota
	 * @return Retorna uma resposta de chamada remota
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public RemoteCallReply checkMessage(RemoteCall rc) throws InstantiationException, IllegalAccessException, ClassNotFoundException;

}