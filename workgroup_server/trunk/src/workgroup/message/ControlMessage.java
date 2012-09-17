package workgroup.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Vector;

/**
 * Classe respons�vel pelos mensagens de controle da aplica��o
 * 
 * @author amadeus
 *
 */
public class ControlMessage extends Message {
	
	private static final long serialVersionUID = 17L;
	
	// Applications message types
	public static final int CONNECT 					= 1;  // param = aplica��o, grupo, login e senha	
	public static final int CONNECT_OK 					= 2;  
	public static final int VIEW_CHANGE 				= 3;  // data = GroupView
	public static final int REQUEST_FLOOR_CONTROL		= 4;
	public static final int TAKE_FLOOR_CONTROL			= 5;
	public static final int LEAVE_FLOOR_CONTROL			= 6;
	
	private int type;
	private Vector<Object> parameters = new Vector<Object>();

	/**
	 * Construtor da classe
	 * 
	 * @param type O tipo da mensagem
	 */
	public ControlMessage(int type) {
		this.type = type;
	}

	/**
	 * Construtor da classe
	 * 
	 * @param type O tipo da mensagem
	 * @param data O objeto (GroupView, que cont�m a lista de usu�rios, uma data
	 * e o nome do grupo) a ser enviado como mensagem (ver construtor de Mensagem)
	 */
	public ControlMessage(int type, Serializable data) {
		super(data);
		this.type = type;
	}

	/**
	 * M�todo que retorna os par�metros aplica��o, gruop, login, senha do usu�rio
	 * 
	 * @return Collection Os par�metros supra citados
	 */
	public Collection getParameters() {
		return parameters;
	}

	/**
	 * M�todo que retorna o tipo da mensagem
	 * 
	 * @return int O tipo da mensagem
	 */
	public int getType() {
		return type;
	}

	/**
	 * M�todo que retorna um par�metro espec�fico atrav�s do �ndice fornecido
	 *  
	 * @param i O �ndice do par�metro desejado
	 * @return Object O par�metro desejado
	 */
	public Object getParameter(int i) {
		return parameters.get(i);
	}
	
	/**
	 * M�todo que adiciona um par�metro � cole��o
	 * 
	 * @param parameter Par�metro a ser adicionado
	 */
	public void addParameter(Object parameter) {
		this.parameters.add(parameter);
	}
	
	/**
	 * M�todo que modifica o tipo da mensagem
	 * 
	 * @param type O tipo da mensagem
	 */
	public void setType(int type) {
		this.type = type;
	}
}
