package controller;

import java.util.ArrayList;
import java.util.List;

import util.ControllerObserver;

/**
 * Classe respons�vel por todos os m�todos relativos a observador 
 * do controlador(adicionar, notificar, etc)
 * 
 * @author amadeus
 * @version 1.0
 */
public abstract class Controller {

	private transient List<ControllerObserver> observers = new ArrayList<ControllerObserver>();
	private boolean change = false;
	
	/**
	 * M�todo que adiciona um observador � lista de observadores
	 * 
	 * @param observer Controlador a ser adicionado � lista de observadores
	 */
	public synchronized void addObserver(ControllerObserver observer) {
		this.observers.add(observer);
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
		return observers.size();
	}

	/**
	 * M�todo que retira um controlador da lista de observadores
	 * 
	 * @param observer Controlador a ser retirado da lista de observadores
	 */
	public synchronized void deleteObserver(ControllerObserver observer) {
		this.observers.remove(observer);
	}

	/**
	 * M�todo que apaga a lista de observadores
	 *
	 */
	public synchronized void deleteObservers() {
		this.observers.clear();
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
		for (ControllerObserver observer : observers) {
			observer.update();
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
	
}
