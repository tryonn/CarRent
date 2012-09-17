package br.ufpe.cin.middleware._old;

import java.io.Serializable;

/**
 * Classe que representa uma chamada remota.
 * 
 * @author Bruno Barros (blbs@cin.ufpe.br)
 * @author Rebeka Gomes (rgo2@cin.ufpe.br)
 * 
 */
public class RemoteCall implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/** o nome completo da classe remota. <i>Ex: <code>java.lang.String</code></i>. */
	public String remoteClass;
	/** o nome do m�todo a ser executado. */
	public String methodName;
	/** um array de <code>Object</code> com os par�metros do m�todo a ser executado,
	 * na mesma ordem em que aparecem na declara��o do m�todo. */
	public Object[] args;
	/** um array de <code>Class</code>, representando o tipo dos par�metros do m�todo a ser executado,
	 * na mesma ordem em que aparecem na declara��o do m�todo. Ex.: <pre>
	 * Object [] parametros = {"Teste", 20, 18.2};
	 * Class [] assinaturas = {String.<b>class</b>, int.<b>class</b>, double.<b>class</b>};
	 * </pre>
	 * */
	public Class[] sigs;
	/**
	 * Cria uma nova chamada remota.
	 * 
	 * @param methodName - nome do m�todo a ser executado.
	 * @param remoteClass - nome completo da classe que cont�m o m�todo a ser executado.
	 */
	public RemoteCall(String methodName, String remoteClass) {
		 this.methodName = methodName;
		 this.remoteClass = remoteClass;
	}	

}
