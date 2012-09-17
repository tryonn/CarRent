package workgroup.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Vector;

/**
 * Classe responsável pelos mensagens de controle da aplicação
 * 
 * @author amadeus
 *
 */
public class ControlMessage extends Message {
	
	private static final long serialVersionUID = 17L;
	
	// Applications message types
	public static final int CONNECT 					= 1;  // param = aplicação, grupo, login e senha	
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
	 * @param data O objeto (GroupView, que contém a lista de usuários, uma data
	 * e o nome do grupo) a ser enviado como mensagem (ver construtor de Mensagem)
	 */
	public ControlMessage(int type, Serializable data) {
		super(data);
		this.type = type;
	}

	/**
	 * Método que retorna os parâmetros aplicação, gruop, login, senha do usuário
	 * 
	 * @return Collection Os parâmetros supra citados
	 */
	public Collection getParameters() {
		return parameters;
	}

	/**
	 * Método que retorna o tipo da mensagem
	 * 
	 * @return int O tipo da mensagem
	 */
	public int getType() {
		return type;
	}

	/**
	 * Método que retorna um parâmetro específico através do índice fornecido
	 *  
	 * @param i O índice do parâmetro desejado
	 * @return Object O parâmetro desejado
	 */
	public Object getParameter(int i) {
		return parameters.get(i);
	}
	
	/**
	 * Método que adiciona um parâmetro à coleção
	 * 
	 * @param parameter Parâmetro a ser adicionado
	 */
	public void addParameter(Object parameter) {
		this.parameters.add(parameter);
	}
	
	/**
	 * Método que modifica o tipo da mensagem
	 * 
	 * @param type O tipo da mensagem
	 */
	public void setType(int type) {
		this.type = type;
	}
}
