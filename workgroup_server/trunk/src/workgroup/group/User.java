package workgroup.group;

import java.io.Serializable;

/**
 * Classe que representa um usuário da aplicação
 * 
 * @author amadeus
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 15L;
	
	String userID;
	boolean control = false;
	boolean controlRequested = false;
	Object userData = null;
	
	/**
	 * Construtor da classe. Inicializa o identificador do usuário com o Id fornecido
	 * 
	 * @param userID O Id do novo usuário
	 */
	public User(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Construtor da classe. Inicializa o identificador do usuário com o Id fornecido
	 * e inicializa os dados do usuário com os dados fornecidos
	 * 
	 * @param userID O Id do novo usuário
	 * @param userData Os dados do novo usuário
	 */
	public User(String userID, Serializable userData) {
		this.userID = userID;
		this.userData = userData;
	}
	
	/**
	 * Método que retorna o Id do usuário
	 * 
	 * @return String O Id do usuário
	 */
	public String getUserID() {
		return userID;
	}
	
	/**
	 * Método que modifica o Id do usuário
	 * 
	 * @param userID O novo Id do usuário
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Método que retorna os dados do usuário
	 * 
	 * @return Object Os dados do usuário
	 */
	public Object getUserData() {
		return userData;
	}
	
	/**
	 * Método que modifica os dados do usuário
	 * 
	 * @param userData Os novos dados do usuário
	 */
	public void setUserData(Serializable userData) {
		this.userData = userData;
	}
	
	/**
	 * Método que retorna se o usuário tem o controle da aplicação
	 * 
	 * @return boolean True se o usuário tem o controle da aplicação e False caso contrário
	 */
	public boolean isControlOwner() {
		return control;
	}
	
	/**
	 * Método que dá o controle ao usuário
	 *
	 */
	protected void getControl() {
		this.control = true;
		this.setControlRequested(false);
	}
	
	/**
	 * Método que retira o controle do usuário
	 *
	 */
	protected void leaveControl() {
		this.control = false;
	}

	/**
	 * Método que retorna se o usuário está querendo ou não o controle da aplicação
	 * 
	 * @return boolean True se o usuário está requisitando o contorle da aplicaçÃo
	 * e False caso contrário
	 */
	public boolean isControlRequested() {
		return controlRequested;
	}
	
	/**
	 * Método que modifica o status da requisição do controle da aplicação
	 * 
	 * @param controlRequested O novo status da requisição do controle da aplicação
	 */
	protected void setControlRequested(boolean controlRequested) {
		this.controlRequested = controlRequested;
	}
	
	/**
	 * Método que retorna o Id do usuário
	 * 
	 * @return String O Id do usuário
	 */
	public String toString() {
		return userID;
	}
}
