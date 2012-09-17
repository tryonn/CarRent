package model.pendulo;

import util.resource.Resource;
import exception.ActionExecuteException;

/**
 * Classe que contém a estrutura de dados do passo Medicao
 * 
 * @author amadeus
 * @version 1.0
 */
public class MedicaoStepModel extends StepModel {
	
	private String altura = "";
	private int instrucao = 1;

	/**
	 * Construtor da classe 
	 *  
	 * @param order Ordem do passo dentre os demais passos do programa
	 */
	public MedicaoStepModel(int order) {
		super(order);
	}

	/**
	 * Método que incrementa a instrução
	 * 
	 * @return boolean O sucesso ou não da operação
	 */
	public boolean next() {
		boolean retorno = false;
		
		//?????
		if (instrucao == 3 && getAltura().equals("")) {
			throw new RuntimeException(Resource.pendulumHeightNotDefined);
		}
		
		if (instrucao < 3) {
			instrucao++;
			
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

	/**
	 * Método que retorna a altura atual do modelo Ajuste
	 * 
	 * @return String Altura atual
	 */
	public String getAltura() {
		return altura;
	}

	/**
	 * Método que substitui a altura atual pela nova altura fornecida como parâmetro
	 * 
	 * @param altura Nova altura
	 */
	public void setAltura(String altura) {
		if (! this.altura.equals(altura)) {
			this.altura = altura;
			setChanged();
			notifyObservers();
		}
	}

}
