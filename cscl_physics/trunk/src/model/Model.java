package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import util.Action;
import util.ModelObserver;
import exception.ActionExecuteException;

/**
 * Classe responsável por todos os métodos relativos a observador 
 * do modelo (adicionar, notificar, etc)
 * 
 * @author amadeus
 * @version 1.0
 */
public abstract class Model implements Serializable {

	private transient List<ModelObserver> observers = new ArrayList<ModelObserver>();
	private boolean change = false;
	
	/**
	 * Método que adiciona um observador à lista de observadores
	 * 
	 * @param observer Modelo a ser adicionado à lista de observadores
	 */
	public synchronized void addObserver(ModelObserver observer) {
		this.getObservers().add(observer);
	}
	
	/**
	 * Método que diz que todos os observadores não sofreram mudanças
	 *
	 */
	protected synchronized void clearChanged() {
		this.change = false;
	}

	/**
	 * Método que retorna o número de observadores da lista de observadores
	 * 
	 * @return int O número de observadores da lista de observadores
	 */
	public synchronized int countObservers() {
		return getObservers().size();
	}

	/**
	 * Método que retira um modelo da lista de observadores
	 * 
	 * @param observer Modelo a ser retirado da lista de observadores
	 */
	public synchronized void deleteObserver(ModelObserver observer) {
		this.getObservers().remove(observer);
	}

	/**
	 * Método que apaga a lista de observadores
	 *
	 */
	public synchronized void deleteObservers() {
		this.getObservers().clear();
	}

	/**
	 * Método que diz se houve ou não alguma mudança nos observadores
	 * 
	 * @return boolean A ocorrência da mudança
	 */
	public synchronized boolean hasChanged() {
		return this.change;
	}

	/**
	 * Método que notifica os observadores que houve uma mudança e que eles 
	 * precisam ser atualizados
	 *
	 */
	public void notifyObservers() {
		for (ModelObserver observer : getObservers()) {
			observer.update(this);
		}
	}

	/**
	 * Método que notifica os observadores que houve uma mudança e que eles 
	 * precisam ser atualizados
	 * 
	 * @param model Modelo a ser passado no método update
	 */
	public void notifyObservers(Model model) {
		for (ModelObserver observer : getObservers()) {
			observer.update(model);
		}
		clearChanged();
	}

	/**
	 * Método que indica que houve uma mudança nos observadores
	 *
	 */
	protected synchronized void setChanged() {
		this.change = true;
	}
	
	/**
	 * Método que executa uma ação provinda de estímulo externo, como o pressionamento
	 * de um botão, etc
	 * 
	 * @param action A ação a ser executada
	 * @throws ActionExecuteException Exceção lançada quando algo inesperado 
	 * ocorre durante a execução da ação
	 */
	public void execute(Action action) throws ActionExecuteException {
		action.execute(this);
	}

	/**
	 * Método que retorna a lista de observadores
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
