package model.pendulo;

import model.Model;
import util.ModelObserver;

/**
 * Classe abstrata respons�vel por encapsular os submodelos
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
	 * M�todo que retorna a ordem do submodelo dentre os demais
	 * 
	 * @return int ordem do submodelo
	 */
	public int getOrder() {
		return order;
	}
	
	/**
	 * M�todo respons�vel por iniciar alguma atividade no momento em que
	 * o submodelo � o que est� sendo visualizado
	 */
	public void onEnter() {
	}

	/**
	 * M�todo respons�vel por iniciar alguma atividade no momento em que
	 * o submodelo � o que deixa de ser visualizado
	 */
	public void onExit() {
	}
	
	/**
	 * M�todo que incrementa o passo (ou instru��o interna do mesmo)
	 * 
	 * @return boolean O sucesso da opera��o
	 */
	public abstract boolean next();
	
	/**
	 * M�todo que decrementa o passo (ou instru��o interna do mesmo)
	 * 
	 * @return boolean O sucesso da opera��o
	 */
	public abstract boolean prior();
	
}
