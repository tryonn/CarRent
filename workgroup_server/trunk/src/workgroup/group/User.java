package workgroup.group;

import java.io.Serializable;

/**
 * Classe que representa um usu�rio da aplica��o
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
	 * Construtor da classe. Inicializa o identificador do usu�rio com o Id fornecido
	 * 
	 * @param userID O Id do novo usu�rio
	 */
	public User(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Construtor da classe. Inicializa o identificador do usu�rio com o Id fornecido
	 * e inicializa os dados do usu�rio com os dados fornecidos
	 * 
	 * @param userID O Id do novo usu�rio
	 * @param userData Os dados do novo usu�rio
	 */
	public User(String userID, Serializable userData) {
		this.userID = userID;
		this.userData = userData;
	}
	
	/**
	 * M�todo que retorna o Id do usu�rio
	 * 
	 * @return String O Id do usu�rio
	 */
	public String getUserID() {
		return userID;
	}
	
	/**
	 * M�todo que modifica o Id do usu�rio
	 * 
	 * @param userID O novo Id do usu�rio
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/**
	 * M�todo que retorna os dados do usu�rio
	 * 
	 * @return Object Os dados do usu�rio
	 */
	public Object getUserData() {
		return userData;
	}
	
	/**
	 * M�todo que modifica os dados do usu�rio
	 * 
	 * @param userData Os novos dados do usu�rio
	 */
	public void setUserData(Serializable userData) {
		this.userData = userData;
	}
	
	/**
	 * M�todo que retorna se o usu�rio tem o controle da aplica��o
	 * 
	 * @return boolean True se o usu�rio tem o controle da aplica��o e False caso contr�rio
	 */
	public boolean isControlOwner() {
		return control;
	}
	
	/**
	 * M�todo que d� o controle ao usu�rio
	 *
	 */
	protected void getControl() {
		this.control = true;
		this.setControlRequested(false);
	}
	
	/**
	 * M�todo que retira o controle do usu�rio
	 *
	 */
	protected void leaveControl() {
		this.control = false;
	}

	/**
	 * M�todo que retorna se o usu�rio est� querendo ou n�o o controle da aplica��o
	 * 
	 * @return boolean True se o usu�rio est� requisitando o contorle da aplica��o
	 * e False caso contr�rio
	 */
	public boolean isControlRequested() {
		return controlRequested;
	}
	
	/**
	 * M�todo que modifica o status da requisi��o do controle da aplica��o
	 * 
	 * @param controlRequested O novo status da requisi��o do controle da aplica��o
	 */
	protected void setControlRequested(boolean controlRequested) {
		this.controlRequested = controlRequested;
	}
	
	/**
	 * M�todo que retorna o Id do usu�rio
	 * 
	 * @return String O Id do usu�rio
	 */
	public String toString() {
		return userID;
	}
}
