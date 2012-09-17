package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import util.Action;
import util.ModelObserver;
import exception.ActionExecuteException;

/**
 * Classe respons�vel por todos os m�todos relativos a observador 
 * do modelo (adicionar, notificar, etc)
 * 
 * @author amadeus
 * @version 1.0
 */
public abstract class Model implements Serializable {

	private transient List<ModelObserver> observers = new ArrayList<ModelObserver>();
	private boolean change = false;
	
	/**
	 * M�todo que adiciona um observador � lista de observadores
	 * 
	 * @param observer Modelo a ser adicionado � lista de observadores
	 */
	public synchronized void addObserver(ModelObserver observer) {
		this.getObservers().add(observer);
	}
	
	/**
	 * M�todo que diz que todos os observadores n�o sofreram mudan�as
	 *
	 */
	protected synchronized void clearChanged() {
		this.change = false;
	}

	/**
	 * M�todo que retorna o n�mero de observadores da lista de observadores
	 * 
	 * @return int O n�mero de observadores da lista de observadores
	 */
	public synchronized int countObservers() {
		return getObservers().size();
	}

	/**
	 * M�todo que retira um modelo da lista de observadores
	 * 
	 * @param observer Modelo a ser retirado da lista de observadores
	 */
	public synchronized void deleteObserver(ModelObserver observer) {
		this.getObservers().remove(observer);
	}

	/**
	 * M�todo que apaga a lista de observadores
	 *
	 */
	public synchronized void deleteObservers() {
		this.getObservers().clear();
	}

	/**
	 * M�todo que diz se houve ou n�o alguma mudan�a nos observadores
	 * 
	 * @return boolean A ocorr�ncia da mudan�a
	 */
	public synchronized boolean hasChanged() {
		return this.change;
	}

	/**
	 * M�todo que notifica os observadores que houve uma mudan�a e que eles 
	 * precisam ser atualizados
	 *
	 */
	public void notifyObservers() {
		for (ModelObserver observer : getObservers()) {
			observer.update(this);
		}
	}

	/**
	 * M�todo que notifica os observadores que houve uma mudan�a e que eles 
	 * precisam ser atualizados
	 * 
	 * @param model Modelo a ser passado no m�todo update
	 */
	public void notifyObservers(Model model) {
		for (ModelObserver observer : getObservers()) {
			observer.update(model);
		}
		clearChanged();
	}

	/**
	 * M�todo que indica que houve uma mudan�a nos observadores
	 *
	 */
	protected synchronized void setChanged() {
		this.change = true;
	}
	
	/**
	 * M�todo que executa uma a��o provinda de est�mulo externo, como o pressionamento
	 * de um bot�o, etc
	 * 
	 * @param action A a��o a ser executada
	 * @throws ActionExecuteException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a execu��o da a��o
	 */
	public void execute(Action action) throws ActionExecuteException {
		action.execute(this);
	}

	/**
	 * M�todo que retorna a lista de observadores
	 * 
	 * @return List<ModelObserver> A lista de observadores
	 */
	protected List<ModelObserver> getObservers() {
		if (observers == null) {
			observers = new ArrayList<ModelObserver>();
		}
		return observers;
	}
		
}
