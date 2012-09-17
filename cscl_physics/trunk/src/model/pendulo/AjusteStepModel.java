package model.pendulo;

import util.ModelObserver;

/**
 * Classe que contém a estrutura de dados do passo Ajuste
 * 
 * @author amadeus
 * @version 1.0
 */
public class AjusteStepModel extends StepModel {
	
	private int instrucao = 1;
	
	/**
	 * Construtor da classe 
	 *  
	 * @param order Ordem do passo dentre os demais passos do programa
	 */
	public AjusteStepModel(int order) {
		super(order);
	}
	
	/**
	 * Método que incrementa a instrução
	 * 
	 * @return boolean O sucesso ou não da operação
	 */
	public boolean next() {
		boolean retorno = false;
		if (instrucao < 2) {
			instrucao++;
			
			// Modelo Alterado
			setChanged();
			notifyObservers();

			retorno = true;
		}
		return retorno;
	}

	/**
	 * Método que decrementa a instrução
	 * 
	 * @return boolean O sucesso ou não da operação
	 */
	public boolean prior() {
		boolean retorno = false;
		if (instrucao > 1) {
			instrucao--;
			
			// Modelo Alterado
			setChanged();
			notifyObservers();

			retorno = true;
		}
		return retorno;
	}

	/**
	 * Método que retorna a instrução atual do modelo Ajuste
	 * 
	 * @return int Instrução atual
	 */
	public int getInstrucao() {
		return instrucao;
	}

}
