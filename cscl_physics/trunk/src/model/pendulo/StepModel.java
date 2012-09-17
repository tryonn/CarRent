package model.pendulo;

import model.Model;
import util.ModelObserver;

/**
 * Classe abstrata responsável por encapsular os submodelos
 * 
 * @author amadeus
 * @version 1.0
 */
public abstract class StepModel extends Model {
	
	protected int order;	

	/**
	 * Construtor da classe
	 * 
	 * @param order Serve para indicar a ordem do submodelo dentre os demais
	 */
	public StepModel(int order) {
		this.order = order;
	}
	
	/**
	 * Método que retorna a ordem do submodelo dentre os demais
	 * 
	 * @return int ordem do submodelo
	 */
	public int getOrder() {
		return order;
	}
	
	/**
	 * Método responsável por iniciar alguma atividade no momento em que
	 * o submodelo é o que está sendo visualizado
	 */
	public void onEnter() {
	}

	/**
	 * Método responsável por iniciar alguma atividade no momento em que
	 * o submodelo é o que deixa de ser visualizado
	 */
	public void onExit() {
	}
	
	/**
	 * Método que incrementa o passo (ou instrução interna do mesmo)
	 * 
	 * @return boolean O sucesso da operação
	 */
	public abstract boolean next();
	
	/**
	 * Método que decrementa o passo (ou instrução interna do mesmo)
	 * 
	 * @return boolean O sucesso da operação
	 */
	public abstract boolean prior();
	
}
