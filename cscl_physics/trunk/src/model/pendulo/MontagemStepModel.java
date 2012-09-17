package model.pendulo;

import util.ModelObserver;

/**
 * Classe que cont�m a estrutura de dados do passo Montagem
 * 
 * @author amadeus
 * @version 1.0
 */
public class MontagemStepModel extends StepModel {
	
	private int instrucao = 1;
	
	/**
	 * Construtor da classe 
	 *  
	 * @param order Ordem do passo dentre os demais passos do programa
	 */
	public MontagemStepModel(int order) {
		super(order);
	}

	/**
	 * M�todo que incrementa a instru��o
	 * 
	 * @return boolean O sucesso ou n�o da opera��o
	 */
	public boolean next() {
		boolean retorno = false;
		if (instrucao < 3) {
			instrucao++;
			
			// Modelo Alterado
			setChanged();
			notifyObservers();

			retorno = true;
		}
		return retorno;
	}

	/**
	 * M�todo que decrementa a instru��o
	 * 
	 * @return boolean O sucesso ou n�o da opera��o
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
	 * M�todo que retorna a instru��o atual do modelo Ajuste
	 * 
	 * @return int Instru��o atual
	 */
	public int getInstrucao() {
		return instrucao;
	}
}
