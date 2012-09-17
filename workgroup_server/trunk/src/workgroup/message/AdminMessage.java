package workgroup.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Vector;

/**
 * Classe responsável pelo gerenciamento das mensagens de administrador
 * 
 * @author amadeus
 *
 */
public class AdminMessage extends Message {
	
	private static final long serialVersionUID = 11L;
	
	// Admin message types
	public static final int ADMIN_REGISTER				= 1; // Parametros: login, senha, porta
	public static final int ADMIN_MODEL_CHANGE			= 2; // Data: Modelo Alterado
	
	private int type;
	private Vector<Object> parameters = new Vector<Object>();

	/**
	 * Construtor da classe
	 * 
	 * @param type O tipo da mensagem do administrador (Register ou Model_Change)
	 */
	public AdminMessage(int type) {
		this.type = type;
	}

	/**
	 * Construtor da classe
	 * 
	 * @param type O tipo da mensagem do administrador (Register ou Model_Change)
	 * @param data O objeto (modelo alterado) a ser enviado como mensagem (ver construtor de Mensagem)
	 */
	public AdminMessage(int type, Serializable data) {
		super(data);
		this.type = type;
	}

	/**
	 * Método que retorna os parâmetros login, senha e porta do usuário
	 * 
	 * @return Collection Os parâmetros supra citados
	 */
	public Collection getParameters() {
		return parameters;
	}

	/**
	 * Método que retorna o tipo da mensagem do administrador
	 * 
	 * @return int O tipo da mensagem do administrador
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
	 * Método que modifica o tipo da mensagem do administrador
	 * 
	 * @param type O tipo da mensagem
	 */
	public void setType(int type) {
		this.type = type;
	}
}
