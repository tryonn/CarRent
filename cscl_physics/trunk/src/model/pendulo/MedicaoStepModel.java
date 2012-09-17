package model.pendulo;

import util.resource.Resource;
import exception.ActionExecuteException;

/**
 * Classe que cont�m a estrutura de dados do passo Medicao
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
	 * M�todo que incrementa a instru��o
	 * 
	 * @return boolean O sucesso ou n�o da opera��o
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
	 * M�todo que decrementa a instru��o
	 * 
	 * @return boolean O sucesso ou n�o da opera��o
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
	 * M�todo que retorna a instru��o atual do modelo Ajuste
	 * 
	 * @return int Instru��o atual
	 */
	public int getInstrucao() {
		return instrucao;
	}

	/**
	 * M�todo que retorna a altura atual do modelo Ajuste
	 * 
	 * @return String Altura atual
	 */
	public String getAltura() {
		return altura;
	}

	/**
	 * M�todo que substitui a altura atual pela nova altura fornecida como par�metro
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
