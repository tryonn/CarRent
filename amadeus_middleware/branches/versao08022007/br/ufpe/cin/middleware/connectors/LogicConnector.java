package br.ufpe.cin.middleware.connectors;

/**
 * Classe que representa um conector lógico.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 * 
 */
public class LogicConnector implements Connector {
	
	private String connector;
	private String[] components;
	
	/**
	 * Construtor da classe LogicConnector.
	 * 
	 * @param connector - nome do conector.
	 * @param components - array de String com os componentes.
	 */
	public LogicConnector(String connector, String[] components) {
		this.connector = connector;
		this.components = components;
	}
	/**
	 * Retorna os nomes dos componentes.
	 * 
	 * @return Retorna o <code>components</code>
	 */
	public String[] getComponents() {
		return components;
	}
	/**
	 * Altera os nomes dos componentes.
	 * 
	 * @param components O novo <code>components</code>.
	 */
	public void setComponents(String[] components) {
		this.components = components;
	}
	/**
	 * Retorna o nome do conector.
	 * 
	 * @return Retorna o <code>connector</code>.
	 */
	public String getConnector() {
		return connector;
	}
	/**
	 * Altera o nome do conector.
	 * 
	 * @param connector - O novo <code>connector</code>.
	 */
	public void setConnector(String connector) {
		this.connector = connector;
	}
	
}
