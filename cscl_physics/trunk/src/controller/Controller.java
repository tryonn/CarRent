package controller;

import java.util.ArrayList;
import java.util.List;

import util.ControllerObserver;

/**
 * Classe responsável por todos os métodos relativos a observador 
 * do controlador(adicionar, notificar, etc)
 * 
 * @author amadeus
 * @version 1.0
 */
public abstract class Controller {

	private transient List<ControllerObserver> observers = new ArrayList<ControllerObserver>();
	private boolean change = false;
	
	/**
	 * Método que adiciona um observador à lista de observadores
	 * 
	 * @param observer Controlador a ser adicionado à lista de observadores
	 */
	public synchronized void addObserver(ControllerObserver observer) {
		this.observers.add(observer);
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
		return observers.size();
	}

	/**
	 * Método que retira um controlador da lista de observadores
	 * 
	 * @param observer Controlador a ser retirado da lista de observadores
	 */
	public synchronized void deleteObserver(ControllerObserver observer) {
		this.observers.remove(observer);
	}

	/**
	 * Método que apaga a lista de observadores
	 *
	 */
	public synchronized void deleteObservers() {
		this.observers.clear();
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
		for (ControllerObserver observer : observers) {
			observer.update();
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
	
}
