package model.pendulo;

import br.ufpe.cin.amadeus.io.ParallelSource;
import br.ufpe.cin.amadeus.io.erimont.ErimontAdapter;
import br.ufpe.cin.amadeus.io.erimont.ErimontObserver;

/**
 * Classe que contém a estrutura de dados do passo Resultado
 * 
 * @author amadeus
 * @version 1.0
 */
public class ResultadoStepModel extends StepModel implements ErimontObserver {

	private static final int TIME_TO_READ = 10;
	private static int countRead = 0;
	
	private boolean reading = false;

	private transient ErimontAdapter erimont = new ErimontAdapter(new ParallelSource());
	private ResultadoTableModel tableModel = new ResultadoTableModel();

	/**
	 * Construtor da classe 
	 *  
	 * @param order Ordem do passo dentre os demais passos do programa
	 */
	public ResultadoStepModel(int order) {
		super(order);
		erimont.setObserver(this);
	}

	/**
	 * Método que retorna false porque este modelo não possui instruções
	 * 
	 * @return boolean Sempre false
	 */
	public boolean next() {
		return false;
	}

	/**
	 * Método que cancela a leitura da porta paralela e retorna false,
	 * já que este modelo não possui instruções 
	 * 
	 * @return boolean Sempre false
	 */
	public boolean prior() {
		if ( isReading() ) {
			cancelRead();
		}
		return false;
	}
	
	/**
	 * Método que inicia a leitura da porta paralela
	 */
	public void onEnter() {
		readSensor();
	}

	/**
	 * Método que lê da porta paralela
	 *
	 */
	public void readSensor() {
		reading = true;
		countRead = 0;
		tableModel.clear();
		erimont.startRead();
	}
	
	/**
	 * Método que cancela a leitura da porta paralela
	 *
	 */
	public void cancelRead() {
		erimont.stopRead();
		reading = false;
		tableModel.clear();
	}

	/**
	 * Método que adiciona mais um tempo à tabela de resultados e notifica
	 * o fim da leitura quando necessário
	 */
	public void interceptSensor(long nanoTime) {
		/* System.out.println(nanoTime);*/
		if (countRead < TIME_TO_READ + 2) {
			tableModel.addTime(nanoTime);
		} else {
			erimont.stopRead();
			reading = false;
			notifyObservers();
		}
		countRead++;
	}

	/**
	 * Método que diz se a leitura da porta paralela ainda está em andamento
	 * 
	 * @return boolean O andamento da leitura
	 */
	public boolean isReading() {
		return reading;
	}

	/**
	 * Método que retorna o modelo da tabela de resultados
	 * 
	 * @return ResultadoTableModel o modelo da tabela de resultados
	 */
	public ResultadoTableModel getTableModel() {
		return tableModel;
	}
}
