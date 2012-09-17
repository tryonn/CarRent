package br.ufpe.cin.middleware._old;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import br.ufpe.cin.middleware.util.Debug;

/**
 * Classe que faz o tratamento e controle de chamadas remotas.
 * 
 * @author Bruno Barros (blbs@cin.ufpe.br)
 * @author Rebeka Gomes (rgo2@cin.ufpe.br)
 *
 */
public class RemoteCallHandler implements RemoteCallHandlerMBean {
	
	/** tabela de objetos remotos */
	Hashtable <String, Object> objectTable = new Hashtable<String, Object>();
	
	/**
	 * Verifica uma chamada remota, retornando a resposta da chamada remota em seguida.
	 * 
	 * @param rc - uma chamada remota
	 * @return Retorna uma resposta de chamada remota
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public RemoteCallReply checkMessage(RemoteCall rc) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		//TODO levanta ou trata as exce��es
		//checa se jah existe uma instancia desta classe executando
		Object o = this.objectTable.get(rc.remoteClass);
		if (o == null) {
			o = this.createRemoteObject(rc.remoteClass);
		}
		return this.executeMethod(rc,o);
	}
	/**
	 * Cria um novo objeto remoto e insere-o na tabela de objetos remotos.
	 * 
	 * @param remoteClass - nome completo da classe remota.
	 * @return Retorna uma referencia para o objeto criado.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Object createRemoteObject(String remoteClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Object o=Class.forName(remoteClass).newInstance();
		this.objectTable.put(remoteClass, o);
		return o;
	}
	/**
	 * Executa um m�todo num objeto remoto, de acordo com o
	 * objeto <code>RemoteCall</code> passado por par�metro
	 * 
	 * @param rc - chamada remota que cont�m informa��es do m�todo que ser� executado.
	 * @param o - objeto remoto no qual o m�todo ser� invocado.
	 * @return um objeto <code>RemoteCallReply</code> que cont�m informa��es
	 * sobre o retorno do m�todo invocado remotamente.
	 */
	public RemoteCallReply executeMethod(RemoteCall rc, Object o) {
		Method m = null;
		RemoteCallReply rcr = null;
		try {
			m = o.getClass().getDeclaredMethod(rc.methodName,rc.sigs);
			Object resp = m.invoke(o, rc.args);
			rcr = new RemoteCallReply(resp);
		} catch (SecurityException e) {
			Debug.printStack(e);
		} catch (NoSuchMethodException e) {
			Debug.printStack(e);
		} catch (IllegalArgumentException e) {
			Debug.printStack(e);
		} catch (IllegalAccessException e) {
			Debug.printStack(e);
		} catch (InvocationTargetException e) {
			Debug.printStack(e);
		}
		return rcr;
	}
}
